package ve.com.lerny.paymentapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

import ve.com.lerny.paymentapp.R;
import ve.com.lerny.paymentapp.models.MethodPaymentResponse;

public class MethodPaymentAdapter extends ArrayAdapter<MethodPaymentResponse>{

    private final Context context;
    private final List<MethodPaymentResponse> values;

    public MethodPaymentAdapter(Context context, List<MethodPaymentResponse> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_method_payment, parent, false);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.label);
            holder.image = convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }  else {
            holder = (ViewHolder) convertView.getTag();
        }
        MethodPaymentResponse item = values.get(position);
        holder.text.setText(item.name);
        Picasso
                .get()
                .load(item.secure_thumbnail)
                .into(holder.image);
        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        TextView text;
    }

}
