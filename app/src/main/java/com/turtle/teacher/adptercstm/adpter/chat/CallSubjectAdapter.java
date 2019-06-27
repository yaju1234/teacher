package com.turtle.teacher.adptercstm.adpter.chat;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.turtle.teacher.R;
import com.turtle.teacher.schoolmodel.HomeWorkSectionModel;
import com.turtle.teacher.schoolmodel.HomeWorkSubjectModel;

import java.util.List;

public class CallSubjectAdapter extends BaseAdapter {
    // Defining List for this class
    private List<HomeWorkSubjectModel> items;

    // Defining Context for this class
    private Context context;

    // Defining int variable for this class
    private int row;

    // Defining TypeFace font component that will be used in this activity
    Typeface centuryGothic;
    Typeface verdana;

    // Defining int variable for this class
    private boolean[] checkedRow;

    // Defining RadioButton Component for this class
    private RadioButton previousSelectedRadio;

    // Defining int variable for this class
    private int previousCheckedPosition = -1;
    // Defining String variable for this class
    private String selected_api_id;


    public CallSubjectAdapter(Context context, int resource,
                              List<HomeWorkSubjectModel> items, String selected_id) {
        this.items = items;
        this.context = context;
        row = resource;
        checkedRow = new boolean[items.size()];
        this.selected_api_id = selected_id;


    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(row, null);

            holder = new ViewHolder();
            holder.row = convertView.findViewById(R.id.rl_row_account);
            holder.radioButton = (RadioButton) convertView
                    .findViewById(R.id.rb_value);
            holder.radioButton.setClickable(false);
            holder.subject_name = (TextView) convertView
                    .findViewById(R.id.tv_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HomeWorkSubjectModel api_Info = items.get(position);

        String sub_id = api_Info.getSUBJECTS_ID();
        String getSUBJECTS_NAME = api_Info.getSUBJECTS_NAME();


        holder.subject_name.setText(getSUBJECTS_NAME);


        if (checkedRow[position])
            holder.radioButton.setChecked(true);
        else
            holder.radioButton.setChecked(false);

        if (previousCheckedPosition == position)
            previousSelectedRadio = holder.radioButton;

        holder.row.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (previousSelectedRadio != null)
                    previousSelectedRadio.setChecked(false);

                if (previousSelectedRadio != null)
                    previousSelectedRadio.setChecked(false);

                holder.radioButton.setChecked(true);
                previousSelectedRadio = holder.radioButton;
                if (previousCheckedPosition > -1)
                    checkedRow[previousCheckedPosition] = false;
                previousCheckedPosition = position;
                checkedRow[position] = true;

            }
        });
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getSelectedPosition() {
        return previousCheckedPosition;
    }



    class ViewHolder {
        View row;
        RadioButton radioButton;
        TextView subject_name;
    }
}
