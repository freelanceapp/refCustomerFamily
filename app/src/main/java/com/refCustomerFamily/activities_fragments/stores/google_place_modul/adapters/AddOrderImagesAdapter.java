package com.refCustomerFamily.activities_fragments.stores.google_place_modul.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.refCustomerFamily.R;
import com.refCustomerFamily.activities_fragments.activity_add_order_product.AddOrderProductActivity;
import com.refCustomerFamily.activities_fragments.activity_package.PackageActivity;
import com.refCustomerFamily.activities_fragments.stores.google_place_modul.activity_fragments.add_order_activity.AddOrderTextActivity;
import com.refCustomerFamily.databinding.AddOrderImagesMoreRowBinding;
import com.refCustomerFamily.databinding.AddOrderImagesRowBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddOrderImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int DATA = 1;
    private final int ADDMORE = 2;
    private List<Uri> list;
    private Context context;
    private LayoutInflater inflater;

    public AddOrderImagesAdapter(List<Uri> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.context = context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == DATA) {
            AddOrderImagesRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_order_images_row, parent, false);
            return new MyHolder(binding);
        } else {
            AddOrderImagesMoreRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_order_images_more_row, parent, false);
            return new AddOrderMoreMoreHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            Uri uri = list.get(position);
            Picasso.get().load(uri).fit().into(myHolder.binding.image);
            myHolder.binding.cardViewDelete.setOnClickListener(v -> {
                if (context instanceof AddOrderTextActivity) {
                    AddOrderTextActivity addOrderTextActivity = (AddOrderTextActivity) context;
                    addOrderTextActivity.delete(myHolder.getAdapterPosition());

                } else if (context instanceof AddOrderProductActivity) {
                    AddOrderProductActivity addOrderTextActivity = (AddOrderProductActivity) context;
                    addOrderTextActivity.delete(myHolder.getAdapterPosition());

                } else if (context instanceof PackageActivity) {
                    PackageActivity addOrderTextActivity = (PackageActivity) context;
                    addOrderTextActivity.delete(myHolder.getAdapterPosition());

                }
            });


        } else if (holder instanceof AddOrderMoreMoreHolder) {
            AddOrderMoreMoreHolder addOrderMoreMoreHolder = (AddOrderMoreMoreHolder) holder;
            addOrderMoreMoreHolder.itemView.setOnClickListener(v -> {

                if (context instanceof AddOrderTextActivity) {
                    AddOrderTextActivity addOrderTextActivity = (AddOrderTextActivity) context;
                    addOrderTextActivity.createDialogAlert();

                } else if (context instanceof AddOrderProductActivity) {
                    AddOrderProductActivity addOrderProductActivity = (AddOrderProductActivity) context;
                    addOrderProductActivity.createDialogAlert();
                } else if (context instanceof PackageActivity) {
                    PackageActivity packageActivity = (PackageActivity) context;
                    packageActivity.createDialogAlert();
                }

            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private AddOrderImagesRowBinding binding;

        public MyHolder(AddOrderImagesRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public static class AddOrderMoreMoreHolder extends RecyclerView.ViewHolder {
        private AddOrderImagesMoreRowBinding binding;

        public AddOrderMoreMoreHolder(AddOrderImagesMoreRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) == null) {
            Log.e("yy", "yy");
            return ADDMORE;
        } else {
            return DATA;
        }
    }
}
