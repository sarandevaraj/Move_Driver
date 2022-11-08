package com.taximobility.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.tripCancel.CreditCardData;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.SessionSave;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter used to show the card details into list view.
 */

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.ViewHolder> {
    private final Context context;
    private List<CreditCardData> mCreditCardList = new ArrayList<>();
    private RecyclerViewItemClickListener mListener;

    public CreditCardAdapter(Context context, RecyclerViewItemClickListener mListener) {
        this.context = context;
        this.mListener = mListener;
    }

    public void submitList(List<CreditCardData> mCreditCardList) {
        this.mCreditCardList = mCreditCardList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.creditcard_item, viewGroup, false);
        Colorchange.ChangeColor((ViewGroup) view, context);
        FontHelper.applyFont(context, view.findViewById(R.id.credit_contain));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder mHolder, int position) {
        try {
            if (position == 0) {
                mHolder.img_lineup.setBackgroundResource(R.drawable.line1);
                mHolder.img_linebottom.setVisibility(View.GONE);
            } else if (position == (mCreditCardList.size() - 1)) {
                mHolder.img_lineup.setBackgroundResource(R.drawable.line2);
                mHolder.img_linebottom.setBackgroundResource(R.drawable.line1);
            } else {
                mHolder.img_lineup.setBackgroundResource(R.drawable.line2);
                mHolder.img_linebottom.setVisibility(View.GONE);
            }
            if (mCreditCardList.size() == 1) {
                mHolder.img_lineup.setBackgroundResource(R.drawable.line1);
                mHolder.img_linebottom.setBackgroundResource(R.drawable.line2);
            }


            final int sdk = android.os.Build.VERSION.SDK_INT;

            if (mCreditCardList.get(position).getOriginal_cardno().toString().startsWith("4")) {

                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mHolder.card_image.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_visa_card) );
                } else {
                    mHolder.card_image.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_visa_card));
                }
            } else
            {
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mHolder.card_image.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_master_card) );
                } else {
                    mHolder.card_image.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_master_card));
                }
            }
         if(position % 2 == 0)
            {
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mHolder.credit_contain.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.corner_over_payment) );
                } else {
                    mHolder.credit_contain.setBackground(ContextCompat.getDrawable(context, R.drawable.corner_over_payment));
                }
            }
            else
            {
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mHolder.credit_contain.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.corner_over_payment_yello) );
                } else {
                    mHolder.credit_contain.setBackground(ContextCompat.getDrawable(context, R.drawable.corner_over_payment_yello));
                }
           }


            String Type = mCreditCardList.get(position).getType();
            String isDef = mCreditCardList.get(position).getDefault_card();
            if (isDef.equals("1")) {
                if (SessionSave.getSession("Lang", context).equals("ar") || SessionSave.getSession("Lang", context).equals("fa")) {
                 //   mHolder.Card.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, R.drawable.credit_card, 0);
                    mHolder.Card.setCompoundDrawablePadding(10);
                } else {
                 //   mHolder.Card.setCompoundDrawablesWithIntrinsicBounds(R.drawable.credit_card, 0, R.drawable.tick, 0);
                    mHolder.Card.setCompoundDrawablePadding(10);
                }
            } else {
                if (SessionSave.getSession("Lang", context).equals("ar") || SessionSave.getSession("Lang", context).equals("fa")) {
                    mHolder.Card.setCompoundDrawablePadding(10);
                } else {
                    mHolder.Card.setCompoundDrawablePadding(10);
                }
            }
            if (Type.equals("P") || Type.equals("B")) {
                //mHolder.Card.setText(mCreditCardList.get(position).getOriginal_cardno());

                String values=mCreditCardList.get(position).getOriginal_cardno();

                mHolder.Card.setText(values.substring(0,4)+" "+values.substring(4,8)+" "+values.substring(8,12)+" "+values.substring(12,16));


            } else if (Type.equals("")) {
                //mHolder.Card.setText(mCreditCardList.get(position).getOriginal_cardno());

                String values=mCreditCardList.get(position).getOriginal_cardno();

                mHolder.Card.setText(values.substring(0,4)+" "+values.substring(4,8)+" "+values.substring(8,12)+" "+values.substring(12,16));

            }

            mHolder.cvv.setText(mCreditCardList.get(position).getName());
            mHolder.date.setText(mCreditCardList.get(position).getMonth()+"/"+mCreditCardList.get(position).getYear());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mCreditCardList.size();
    }

    public interface RecyclerViewItemClickListener {
        void onClick(CreditCardData cardData, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView Card,cvv,date;
        ImageView img_lineup;
        ImageView img_linebottom;
        ImageButton card_image;
        LinearLayout credit_contain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card = itemView.findViewById(R.id.CardTxt);
            cvv = itemView.findViewById(R.id.cvv);
            date = itemView.findViewById(R.id.date);
            img_lineup = itemView.findViewById(R.id.img_lineup);
            img_linebottom = itemView.findViewById(R.id.img_linebottom);
            credit_contain = itemView.findViewById(R.id.credit_contain);
            card_image= itemView.findViewById(R.id.card_image);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                mListener.onClick(mCreditCardList.get(position), position);
            });
        }
    }
}