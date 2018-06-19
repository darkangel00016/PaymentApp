package ve.com.lerny.paymentapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import ve.com.lerny.paymentapp.R;
import ve.com.lerny.paymentapp.models.InstallmentsCostsResponse;

public class InstallmentsAdapter extends ArrayAdapter<InstallmentsCostsResponse>{

    private final Context context;
    private final List<InstallmentsCostsResponse> values;

    public InstallmentsAdapter(Context context, List<InstallmentsCostsResponse> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        }  else {
            holder = (ViewHolder) convertView.getTag();
        }
        InstallmentsCostsResponse item = values.get(position);
        holder.text.setText(item.recommended_message);
        return convertView;
    }

    static class ViewHolder {
        TextView text;
    }

}
