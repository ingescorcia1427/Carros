package com.example.cuc.carros;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by CUC on 3/06/2017.
 */

public class AdaptadorCarro extends RecyclerView.Adapter<AdaptadorCarro.CarroViewHolder> {

    private ArrayList<Carro> carros;
    private OnCarroClickListener clickListener;

    public AdaptadorCarro(ArrayList<Carro> carros, OnCarroClickListener clickListener) {
        this.carros = carros;
        this.clickListener = clickListener;
    }


    @Override
    public AdaptadorCarro.CarroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_carro,parent,false);
        return new CarroViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorCarro.CarroViewHolder holder, int position) {
        final Carro car = carros.get(position);
        Picasso.with(holder.view.getContext()).load(car.getUrlfoto()).into(holder.foto);

        holder.nchasis.setText(car.getNchasis());
        holder.marca.setText(car.getMarca());
        holder.modelo.setText(car.getModelo());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onCarroClick(car);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carros.size();
    }


    public static class CarroViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView nchasis;
        private TextView marca;
        private TextView color;
        private TextView modelo;
        private View view;

        public CarroViewHolder(View itemView) {
            super(itemView);
            view= itemView;
            foto = (ImageView)itemView.findViewById(R.id.foto);
            nchasis = (TextView)itemView.findViewById(R.id.txtNchasisCar);
            marca = (TextView) itemView.findViewById(R.id.txtMarcaCar);
            color = (TextView)itemView.findViewById(R.id.txtMarcaCar);
            modelo = (TextView)itemView.findViewById(R.id.txtModeloCar);
        }
    }


    public interface OnCarroClickListener{
        void onCarroClick(Carro car);
    }

}
