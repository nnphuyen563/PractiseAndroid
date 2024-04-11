    package com.example.midtermk20;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.DatabaseErrorHandler;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.database.sqlite.SQLiteStatement;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.Canvas;
    import android.graphics.drawable.BitmapDrawable;
    import android.graphics.drawable.Drawable;
    import android.util.Log;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;

    import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream;

    public class Databases extends SQLiteOpenHelper {

        private Context context;
        public static final String DB_NAME = "books.sqlite";
        public static final int DB_VERSION = 1;
        public static final String TBL_NAME = "books";
        public static final String COL_ID = "BookId";
        public static final String COL_NAME = "BookName";
        public static final String COL_PUB = "BookPublication";
        public static final String COL_PRINT = "BookPrint";
        public static final String COL_PRICE = "BookPrice";
        public static final String COL_COVER = "BookCover";

        public Databases(@Nullable Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE IF NOT EXISTS " + TBL_NAME+ " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NAME + " VARCHAR(100), " +
                    COL_PUB + " VARCHAR(100), " +
                    COL_PRINT + " INTEGER, " +
                    COL_PRICE + " REAL, " +
                    COL_COVER + " BLOB)";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
            onCreate(db);
        }
        public Cursor queryData(String sql) {
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery(sql, null);
        }

        //INSERT, UPDATE, DELETE
        public boolean execSql(String sql){
            SQLiteDatabase db = getWritableDatabase();
            try {
                db.execSQL(sql);
                return true;
            } catch (Exception e) {
                Log.i("Error", e.toString());
                return false;
            }
        };

        public int numbOfRows() {
            Cursor c = queryData("SELECT * FROM "+ TBL_NAME);
            int numbOfRows = c.getCount();
            c.close();
            return numbOfRows;
        }

        public long insertBook(String bookName, String bookPublication, int bookPrint, double bookPrice, byte[] bookCover) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_NAME, bookName);
            values.put(COL_PUB, bookPublication);
            values.put(COL_PRINT, bookPrint);
            values.put(COL_PRICE, bookPrice);
            values.put(COL_COVER, bookCover);

            long newRowId = db.insert(TBL_NAME, null, values);
            db.close();
            return newRowId;
        }

//        public void createSampleData() {
//            String[] bookNames = {"The Lord of the Rings", "Pride and Prejudice", "To Kill a Mockingbird", "The Hitchhiker's Guide to the Galaxy", "Harry Potter and the Sorcerer's Stone"};
//            String[] bookPublications = {"Allen & Unwin", "T. Egerton Smith", "J. B. Lippincott & Co.", "Pan Books", "Bloomsbury Publishing"};
//            int[] bookPrints = {1954, 1813, 1960, 1979, 1997};
//            double[] bookPrices = {19.99, 12.50, 14.00, 9.99, 10.99};
//
//        // Assuming you have a method to insert data into your database (insertBook)
//            for (int i = 0; i < bookNames.length; i++) {
//                // Convert potential image (replace with your actual image conversion logic)
//                byte[] bookCover = convertImageToByteArray(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background));
//                insertBook(bookNames[i], bookPublications[i], bookPrints[i], bookPrices[i], bookCover);
//            }
//
//        }
        public static Bitmap drawableToBitmap (Drawable drawable) {

            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable)drawable).getBitmap();
            }

            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        }
        public void createSampleData() {
            SQLiteDatabase db = getWritableDatabase();

            String[] bookNames = {"The Lord of the Rings", "Pride and Prejudice", "To Kill a Mockingbird", "The Hitchhiker's Guide to the Galaxy", "Harry Potter and the Sorcerer's Stone"};
            String[] bookPublications = {"Allen & Unwin", "T. Egerton White", "J. B. Lippincott & Co.", "Pan Books", "Bloomsbury Publishing"};
            int[] bookPrints = {1954, 1813, 1960, 1979, 1997};
            double[] bookPrices = {19.99, 12.50, 14.00, 9.99, 10.99};

            Drawable d_cover = context.getResources().getDrawable(R.drawable.ic_launcher_background, context.getTheme());
            Bitmap bitmap = drawableToBitmap(d_cover);

            byte[] bookCover = new byte[0];
            if (context != null) {
                bookCover = convertImageToByteArray(bitmap);
                if (bookCover == null) {
                    Log.e("Database", "Failed to load book cover");
                }
            } else {
                // Handle the case where context is null, or consider throwing an exception.
                Log.e("Database", "Context is null");
            }

            if (numbOfRows() == 0) { // Check to ensure we only insert sample data if the table is empty.
                String insertQuery = "INSERT INTO " + TBL_NAME + " (" + COL_NAME + ", " + COL_PUB + ", " + COL_PRINT + ", " + COL_PRICE + ", " + COL_COVER + ") VALUES (?, ?, ?, ?, ?)";
                SQLiteStatement insertStmt = db.compileStatement(insertQuery);

                db.beginTransaction(); // Start transaction for batch insert.
                try {
                    for (int i = 0; i < bookNames.length; i++) {
                        insertStmt.clearBindings(); // Clear previous bindings.

                        // Bind the values for the current row.
                        insertStmt.bindString(1, bookNames[i]); // Assuming 1st '?' in the query is for book name.
                        insertStmt.bindString(2, bookPublications[i]); // 2nd '?' for publication.
                        insertStmt.bindLong(3, bookPrints[i]); // 3rd '?' for print year (int should be bound with bindLong).
                        insertStmt.bindDouble(4, bookPrices[i]); // 4th '?' for price.
                        insertStmt.bindBlob(5, bookCover); // 5th '?' for book cover image as blob.

                        insertStmt.executeInsert(); // Execute the insert statement.
                    }
                    db.setTransactionSuccessful(); // Mark the transaction as successful.
                } finally {
                    db.endTransaction(); // End the transaction. If successful flag not set, changes will be rolled back.
                }
                db.close(); // Close the database connection.
            }
        }

        public static byte[] convertImageToByteArray(Bitmap bitmap) {
            if (bitmap == null) {
                Log.e("Database", "Null bitmap");
                return null;
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, /* quality 0-100 */ 80, outputStream);
            return outputStream.toByteArray();
        }
    }
