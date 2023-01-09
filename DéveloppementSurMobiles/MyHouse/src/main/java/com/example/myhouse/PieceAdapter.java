package com.example.myhouse;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PieceAdapter extends RecyclerView.Adapter<PieceAdapter.ViewHolder> {
    Activity activity;
    ArrayList<Piece> pieces;
    Piece pieceSelected;
    int numPieceSelected;

    public PieceAdapter(Activity activity, ArrayList<Piece> arrayList) {
        numPieceSelected =0;
        pieces = new ArrayList<>();
        this.activity = activity;
        this.pieces = arrayList;
        pieceSelected = null;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_piece,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Piece piece = pieces.get(position);
        //Piece pieces = arrayList.get(position);

        holder.PieceName.setText(piece.getPieceName());
        holder.PieceNumber.setText(piece.getPieceNumber());

    }

    @Override
    public int getItemCount() {
        return pieces.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView PieceName,PieceNumber;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            PieceName = itemView.findViewById(R.id.piece_name);
            PieceNumber = itemView.findViewById(R.id.piece_number);

            PieceName.setBackgroundColor(Color.parseColor("#9370DB"));
            PieceName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PieceName.setBackgroundColor(Color.RED);
                    pieceSelected = pieces.get(getBindingAdapterPosition());
                    numPieceSelected = getBindingAdapterPosition();
                }
            });
        }

        public void resetBackgroundColor(String s){
            PieceName.setBackgroundColor(Color.parseColor("#9370DB"));
        }
    }

    public Piece getPieceSelected() {
        return pieceSelected;
    }

    public void setPieceSelected(Piece pieceSelected) {
        this.pieceSelected = pieceSelected;
    }

    public void removeItem(int position) {
        pieces.remove(pieceSelected);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, pieces.size());
    }
}
