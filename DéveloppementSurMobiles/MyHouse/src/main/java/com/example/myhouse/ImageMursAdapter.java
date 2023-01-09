package com.example.myhouse;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

    public class ImageMursAdapter extends RecyclerView.Adapter<ImageMursAdapter.ViewHolder> {
        Activity activity;
        public ArrayList<Mur> imageViewsMurs;
        Mur murSelected;

        public ImageMursAdapter(Activity activity, ArrayList<Mur> ims) {
            imageViewsMurs = new ArrayList<>();
            this.activity = activity;
            this.imageViewsMurs = ims;
            murSelected = null;
            notifyDataSetChanged();
        }

        public Bitmap stringToImage(String s){
            byte[] imageBytes = Base64.decode(s, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            return decodedImage;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portes,parent,false);
            return new ImageMursAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageMursAdapter.ViewHolder holder, int position) {
            String im = imageViewsMurs.get(position).mur;
            holder.imagemur.setImageBitmap(stringToImage(im));
        }

        @Override
        public int getItemCount() {
            return 4;
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imagemur;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imagemur = itemView.findViewById(R.id.imageViewMur);

                imagemur.setBackgroundColor(Color.parseColor("#9370DB"));
                imagemur.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imagemur.setBackgroundColor(Color.RED);
                        murSelected = imageViewsMurs.get(getAdapterPosition());
                    }
                });
            }
        }
        public Mur getPieceSelected() {
            return murSelected;
        }
        public void setPieceSelected(Mur mSelected) {
            this.murSelected = mSelected;
        }

    }


