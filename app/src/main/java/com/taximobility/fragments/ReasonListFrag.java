package com.taximobility.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.bookingmodule.BookTaxiHomePage;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.service.GetPassengerUpdate;
import com.taximobility.tripCancel.fragments.CancellationPaymentOptionsFragment;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * this class shows the alert to cancel the trip
 */
public class ReasonListFrag extends BottomSheetDialogFragment implements View.OnClickListener {
    ImageView img1, img2, img3, img4, img5, img6;
    TextView txt1, txt2, txt3, txt4, txt5, txt6;
    public String sReason;
    public Dialog r_mDialog, cvv_Dialog;
    private Dialog alertmDialog;
    public int mTripid, from = 1;
    View v;
    private ViewGroup lay_reason_six, lay_reason_one,
            lay_reason_three,
            lay_reason_two,
            lay_reason_four,
            lay_reason_five, reasonotherlay;
    EditText reasonother;
    Button reson_submit;

    Dialog dialog1;
    CardView card_lay_bottom;

    TextView txtCancelFare;
    String cancelFare = "";

    LinearLayout cancel_lay;
    View cancel_lay_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.reason_list, container, false);
    //    Colorchange.ChangeColor((ViewGroup) v, getActivity());

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Bundle bb = getArguments();
        if (bb != null) {
            if (bb.getString("trip_id") != null)
                mTripid = Integer.parseInt(bb.getString("trip_id"));
            if (bb.getString("Cancel_fee") != null)
                cancelFare = bb.getString("Cancel_fee");
            if (bb.getString("From") != null)
                from = Integer.parseInt(bb.getString("From"));
        } else if (!SessionSave.getSession("trip_id", getActivity()).trim().equals("")) {
            mTripid = Integer.parseInt(SessionSave.getSession("trip_id", getActivity()));
        }
        card_lay_bottom = v.findViewById(R.id.card_lay_bottom);
        card_lay_bottom.setBackgroundResource(R.drawable.corner_over_wallet);
        card_lay_bottom.setCardElevation(20);
        img1 = v.findViewById(R.id.check1);
        img2 = v.findViewById(R.id.check2);
        img3 = v.findViewById(R.id.check3);
        img4 = v.findViewById(R.id.check4);
        img5 = v.findViewById(R.id.check5);
        img6 = v.findViewById(R.id.check6);

        txt1 = v.findViewById(R.id.reason1);
        txt2 = v.findViewById(R.id.reason2);
        txt3 = v.findViewById(R.id.reason3);
        txt4 = v.findViewById(R.id.reason4);
        txt5 = v.findViewById(R.id.reason5);
        txt6 = v.findViewById(R.id.reason6);

        lay_reason_one = v.findViewById(R.id.lay_reason_one);
        lay_reason_three = v.findViewById(R.id.lay_reason_three);
        lay_reason_two = v.findViewById(R.id.lay_reason_two);
        lay_reason_four = v.findViewById(R.id.lay_reason_four);
        lay_reason_five = v.findViewById(R.id.lay_reason_five);
        lay_reason_six = v.findViewById(R.id.lay_reason_six);
        reasonotherlay = v.findViewById(R.id.reasonotherlay);
        reasonother = v.findViewById(R.id.reasonother);
        reson_submit = v.findViewById(R.id.reson_submit);
        cancel_lay = v.findViewById(R.id.cancel_lay);
        cancel_lay_view = v.findViewById(R.id.cancel_lay_view);


        txtCancelFare = v.findViewById(R.id.txt_cancel_fare);

//        if (from == 2) {
//            cancel_lay.setVisibility(View.GONE);
//            cancel_lay_view.setVisibility(View.VISIBLE);
//        } else {
//            cancel_lay.setVisibility(View.VISIBLE);
//            cancel_lay_view.setVisibility(View.GONE);
//        }
        if (from == 2) {
            cancel_lay.setVisibility(View.GONE);
            cancel_lay_view.setVisibility(View.VISIBLE);
        } else {
            cancel_lay.setVisibility(View.GONE);
            cancel_lay_view.setVisibility(View.VISIBLE);
        }

        txtCancelFare.setText(SessionSave.getSession("Currency", getActivity()) + cancelFare);
        ClickMethod();

        return v;
    }

    public void ClickMethod() {

        lay_reason_one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateUI(1);
                sReason = txt1.getText().toString();
                if (from != 2) {
                    if (SessionSave.getSession(TaxiUtil.CANCELLATION_FARE_APPLICABLE, requireContext(), false))
                        showCancelOptions();
                    else {
                        cancelTripApiCall();
                    }
                } else {
                    cancelTripApiCall();
                }
            }
        });

        lay_reason_two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateUI(2);
                sReason = txt2.getText().toString();
                if (from != 2) {
                    if (SessionSave.getSession(TaxiUtil.CANCELLATION_FARE_APPLICABLE, requireContext(), false))
                        showCancelOptions();
                    else {
                        cancelTripApiCall();
                    }
                } else {
                    cancelTripApiCall();
                }
            }
        });

        lay_reason_three.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateUI(3);
                sReason = txt3.getText().toString();
                if (from != 2) {
                    if (SessionSave.getSession(TaxiUtil.CANCELLATION_FARE_APPLICABLE, requireContext(), false))
                        showCancelOptions();
                    else {
                        cancelTripApiCall();
                    }
                } else {
                    cancelTripApiCall();
                }
            }
        });
        lay_reason_four.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateUI(4);
                sReason = txt4.getText().toString();

                if (from != 2) {
                    if (SessionSave.getSession(TaxiUtil.CANCELLATION_FARE_APPLICABLE, requireContext(), false))
                        showCancelOptions();
                    else {
                        cancelTripApiCall();
                    }
                } else {
                    cancelTripApiCall();
                }
            }
        });

        lay_reason_five.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateUI(5);
                sReason = txt5.getText().toString();
                if (from != 2) {
                    if (SessionSave.getSession(TaxiUtil.CANCELLATION_FARE_APPLICABLE, requireContext(), false))
                        showCancelOptions();
                    else {
                        cancelTripApiCall();
                    }
                } else {
                    cancelTripApiCall();
                }
            }
        });
        lay_reason_six.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateUI(6);

            }
        });

        reson_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sReason = reasonother.getText().toString();
                if (!sReason.trim().equals("")) {
                    if (from != 2) {
                        if (SessionSave.getSession(TaxiUtil.CANCELLATION_FARE_APPLICABLE, requireContext(), false))
                            showCancelOptions();
                        else {
                            cancelTripApiCall();
                        }
                    } else {
                        cancelTripApiCall();
                    }
                } else {
                    CToast.ShowToast(getActivity(), NC.getString(R.string.enter_valid_comment));
                }
            }
        });

    }

    public void cancelTripApiCall() {
        try {
            JSONObject j = new JSONObject();
            j.put("passenger_log_id", mTripid);
            j.put("travel_status", "4");
            j.put("remarks", sReason);
            j.put("pay_mod_id", "2");
            j.put("creditcard_cvv", "");
            new Cancel_afterTrip("type=cancel_trip", j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (dialog1 != null)
            Utility.closeDialog(dialog1);
        super.onDestroy();
    }

    /**
     * this method is used to check the image which is selected
     */

    public void UpdateUI(int num) {
        img1.setImageResource(R.drawable.tick_unfocus);
        img2.setImageResource(R.drawable.tick_unfocus);
        img3.setImageResource(R.drawable.tick_unfocus);
        img4.setImageResource(R.drawable.tick_unfocus);
        img5.setImageResource(R.drawable.tick_unfocus);
        img6.setImageResource(R.drawable.tick_unfocus);
        reasonotherlay.setVisibility(View.GONE);

        switch (num) {
            case 1:
                img1.setImageResource(R.drawable.tick_focus);
                break;

            case 2:
                img2.setImageResource(R.drawable.tick_focus);
                break;

            case 3:
                img3.setImageResource(R.drawable.tick_focus);
                break;

            case 4:
                img4.setImageResource(R.drawable.tick_focus);
                break;


            case 5:
                img5.setImageResource(R.drawable.tick_focus);
                break;

            case 6:
                img6.setImageResource(R.drawable.tick_focus);
                reasonotherlay.setVisibility(View.VISIBLE);
                break;

            default:
                img1.setImageResource(R.drawable.tick_unfocus);
                img2.setImageResource(R.drawable.tick_unfocus);
                img3.setImageResource(R.drawable.tick_unfocus);
                img4.setImageResource(R.drawable.tick_unfocus);
                img5.setImageResource(R.drawable.tick_unfocus);
                img6.setImageResource(R.drawable.tick_unfocus);

        }

    }

    public void alert_view(Context mContext, String title, String message, String success_txt, String failure_txt) {
        try {


            final View view = View.inflate(mContext, R.layout.alert_view, null);
            alertmDialog = new Dialog(mContext, R.style.dialogwinddow);
            alertmDialog.setContentView(view);
            alertmDialog.setCancelable(true);
            FontHelper.applyFont(mContext, alertmDialog.findViewById(R.id.alert_id));
            alertmDialog.show();
            final TextView title_text = alertmDialog.findViewById(R.id.title_text);
            final TextView message_text = alertmDialog.findViewById(R.id.message_text);
            final Button button_success = alertmDialog.findViewById(R.id.button_success);
            final Button button_failure = alertmDialog.findViewById(R.id.button_failure);
            button_failure.setVisibility(View.GONE);
            title_text.setText(title);
            message_text.setText(message);
            button_success.setText(success_txt);
            button_success.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    alertmDialog.dismiss();
                }
            });
            button_failure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    alertmDialog.dismiss();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * this class call the api after the trip is cancelled
     */

    private class Cancel_afterTrip implements APIResult {

        String alert_message = "";

        public Cancel_afterTrip(String url, JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON(getActivity(), this, data, false, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession("Lang", getActivity()) + "&" + url).execute();
            // new APIService_Volley_JSON(getActivity(), getActivity(), data, false).execute(url);
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {

            try {
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    Log.e("result****", "result" + result + "_____" + SessionSave.getSession("multi_tripID", getActivity()));
                    if (json.getInt("status") == 1) {
                        //SessionSave.saveSession("trip_id", "", getActivity());
                        if (SessionSave.getSession("multi_tripID", getActivity()).equals("") || SessionSave.getSession("multi_tripID", getActivity()).equals(mTripid))
                            SessionSave.saveSession("trip_id", "", getActivity());
                        SessionSave.saveSession("TaxiStatus", "", getActivity());
                        alert_message = json.getString("message") + "\n" + NC.getResources().getString(R.string.canceled_amount) + " " + SessionSave.getSession("Currency", getActivity()) + json.getString("cancellation_amount") + "\n" + getResources().getString(R.string.canceled_from) + " " + json.getString("cancellation_from");
                        Intent intent = new Intent(getContext(), GetPassengerUpdate.class);
                        requireContext().stopService(intent);
                        if (from == 2) {
                            //  dismiss();
                            TaxiUtil.close = 1;
                            CToast.ShowToast(getActivity(), alert_message);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new TripHistory()).commit();
                        } else {
//                            Intent i_gettaxi = new Intent(getActivity(), MainHomeFragmentActivity.class);
//                            i_gettaxi.putExtra("alert_message", alert_message);
//                            startActivity(i_gettaxi);
                            CToast.ShowToast(getActivity(), alert_message);
                            BookTaxiHomePage.Companion.setBookingState(BookTaxiHomePage.BOOKINGSTATE.STATE_ONE);
//                            HomePage.booking_state = HomePage.BOOKINGSTATE.STATE_ONE;
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new BookTaxiHomePage()).commitAllowingStateLoss();
                            //  getActivity().finish();
                        }


                    } else if (json.getInt("status") == 2) {


                        if (SessionSave.getSession("multi_tripID", getActivity()).equals("") || SessionSave.getSession("multi_tripID", getActivity()).equals(mTripid))
                            SessionSave.saveSession("trip_id", "", getActivity());
                        SessionSave.saveSession("TaxiStatus", "", getActivity());
                        //SessionSave.saveSession("trip_id", "", getActivity());
                        SessionSave.saveSession("TaxiStatus", "", getActivity());
                        alert_message = json.getString("message");
                        Intent intent = new Intent(getContext(), GetPassengerUpdate.class);
                        requireContext().stopService(intent);

                        if (from == 2) {
                            //   dismiss();
                            TaxiUtil.close = 1;
                            CToast.ShowToast(getActivity(), alert_message);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new TripHistory()).commit();
                        } else {
                            Intent i_gettaxi = new Intent(getActivity(), MainHomeFragmentActivity.class);
                            i_gettaxi.putExtra("alert_message", alert_message);
//                            startActivity(i_gettaxi);
//                           getActivity().finish();
                            CToast.ShowToast(getActivity(), alert_message);
//                            HomePage.booking_state = HomePage.BOOKINGSTATE.STATE_ONE;
                            BookTaxiHomePage.Companion.setBookingState(BookTaxiHomePage.BOOKINGSTATE.STATE_ONE);
                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new BookTaxiHomePage()).commit();
                            //    getActivity().finish();
                        }
                    } else if (json.getInt("status") == -1) {
                        CToast.ShowToast(getActivity(), json.getString("message"));
                        SessionSave.saveSession("TaxiStatus", "", getActivity());
                        if (SessionSave.getSession("multi_tripID", getActivity()).equals("") || SessionSave.getSession("multi_tripID", getActivity()).equals(mTripid))
                            SessionSave.saveSession("trip_id", "", getActivity());
                        SessionSave.saveSession("TaxiStatus", "", getActivity());
                        //SessionSave.saveSession("trip_id", "", getActivity());
                        alert_message = json.getString("message");
                        Intent intent = new Intent(getContext(), GetPassengerUpdate.class);
                        requireContext().stopService(intent);

                        if (from == 2) {
                            TaxiUtil.close = 1;
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new TripHistory()).commit();
                        } else {
                            Intent i_gettaxi = new Intent(getActivity(), MainHomeFragmentActivity.class);
                            i_gettaxi.putExtra("alert_message", alert_message);
                            CToast.ShowToast(getActivity(), alert_message);
//                            HomePage.booking_state = HomePage.BOOKINGSTATE.STATE_ONE;
                            BookTaxiHomePage.Companion.setBookingState(BookTaxiHomePage.BOOKINGSTATE.STATE_ONE);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new BookTaxiHomePage()).commit();
                            //     getActivity().finish();
                        }
                    } else if (json.getInt("status") == 3) {
                        CToast.ShowToast(getActivity(), json.getString("message"));
                    } else {
                        CToast.ShowToast(getActivity(), json.getString("message"));
                    }
                    // if (from == 2) {
                    if (getDialog() != null)
                        dismiss();
//                    } else {
//
//
//                    }

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error));
                        }
                    });
                    //                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStop() {
        Systems.out.println("onStop Reason list");
        super.onStop();
    }

    private void showCancelOptions() {

      /*  FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        CancellationPaymentOptionsFragment fragment = CancellationPaymentOptionsFragment.newInstance(mTripid, cancelFare, sReason, from, "", null);
        fragment.show(fragmentManager, "cancellationPaymentOptionsFragment");
        if (getDialog() != null)
            dismiss();*/
        setDelayForCancel();
    }

    private void setDelayForCancel() {
        if (!SessionSave.getSession(TaxiUtil.PASSENGER_GRACE_TIME, getActivity()).isEmpty()) {
            long enableTime = 0L, driverArrivedTime = 0L, currentTime = 0L;
            try {
                driverArrivedTime = Long.parseLong(SessionSave.getSession(TaxiUtil.PASSENGER_TRIP_TIME, getActivity()));
                enableTime = Long.parseLong(SessionSave.getSession(TaxiUtil.PASSENGER_GRACE_TIME, getActivity())) * (1000 * 60); //300000
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            currentTime = System.currentTimeMillis();
            Systems.out.println("setDelayForCancel(): " + enableTime + "**" + driverArrivedTime + "**" + (currentTime - driverArrivedTime) + "**" + currentTime);
            if ((currentTime - driverArrivedTime) > enableTime) {
                Systems.out.println("setDelayForCancel(): 1");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                CancellationPaymentOptionsFragment fragment = CancellationPaymentOptionsFragment.newInstance(mTripid, cancelFare, sReason, from, "", null);
                fragment.show(fragmentManager, "cancellationPaymentOptionsFragment");
                if (getDialog() != null)
                    dismiss();
            } else {
                Systems.out.println("setDelayForCancel(): 2 "+(enableTime - (currentTime - driverArrivedTime)));
//                TripcancelTxt.setVisibility(View.GONE);
                cancelTripApiCall();
            }
        } else
            showCancelOptions();
    }
}
