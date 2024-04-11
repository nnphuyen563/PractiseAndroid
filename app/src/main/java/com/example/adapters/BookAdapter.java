package com.example.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermk20.R;
import com.example.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    Context context;
    List<Book> books;


    public BookAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public
    ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.book_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        final Book book = books.get(position);
        byte[] image = book.getBookCover();

        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,image.length);
        holder.imvBookCover.setImageBitmap(bitmap);
        holder.txtBookName.setText(book.getBookName());
        holder.txtBookId.setText(String.valueOf(book.getBookId()));
        holder.txtBookPrint.setText(String.valueOf(book.getBookPrint()));
        holder.txtBookPublication.setText(book.getBookId());
        holder.txtBookPrice.setText(String.format("%.0f", book.getBookPrice()) + "đ");
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvBookCover;
        TextView txtBookName, txtBookPrice, txtBookId, txtBookPublication, txtBookPrint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imvBookCover = itemView.findViewById(R.id.imvBookCover);
            txtBookName = itemView.findViewById(R.id.txtBookName);
            txtBookId = itemView.findViewById(R.id.txtBookId);
            txtBookPrice = itemView.findViewById(R.id.txtBookPrice);
            txtBookPublication = itemView.findViewById(R.id.txtBookPublication);
            txtBookPrint = itemView.findViewById(R.id.txtBookPrint);

        }
    }
}
