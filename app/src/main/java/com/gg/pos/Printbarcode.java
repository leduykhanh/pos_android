package com.gg.pos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gg.pos.functions.CashDrawerFunctions;
import com.gg.pos.functions.PrinterFunctions;
import com.gg.pos.localizereceipts.ILocalizeReceipts;
import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Mytoast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.IConnectionCallback;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExtManager;
import com.starmicronics.starioextension.StarIoExtManagerListener;

/**
 * Created by Go Goal on 5/30/2017.
 */

public class Printbarcode extends Activity implements IConnectionCallback, CommonAlertDialogFragment.Callback {


    RelativeLayout close;
    Button create;
    String barcode;
    ImageView imageView;
    private StarIoExtManager mStarIoExtManager;
    private int mSelectedIndex;
    private int mLanguage;
    private int mPaperSize;
    private ProgressDialog mProgressDialog;
    private int PRINTER_SET_REQUEST_CODE=1;
    private PrinterSetting setting;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcodeprint);
        setting = new PrinterSetting(this);
        StarIoExt.Emulation emulation = setting.getEmulation();
        mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, setting.getPortName(), setting.getPortSettings(), 10000, getApplicationContext());     // 10000mS!!!
        mStarIoExtManager.setCashDrawerOpenActiveHigh(setting.getCashDrawerOpenActiveHigh());
        mLanguage=PrinterSetting.LANGUAGE_ENGLISH;
        mPaperSize=PrinterSetting.PAPER_SIZE_ESCPOS_THREE_INCH;
        barcode=getIntent().getExtras().getString("a");

        close= (RelativeLayout) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        create= (Button) findViewById(R.id.createbtn);
        imageView= (ImageView) findViewById(R.id.pbiv1);


        Bitmap mBitmap = null;
        com.google.zxing.Writer c9 = new Code128Writer();
        try {
            BitMatrix bm = c9.encode(barcode+"", BarcodeFormat.CODE_128,350, 350);
            mBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < 350; i++) {
                for (int j = 0; j < 350; j++) {
                    mBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (mBitmap != null) {
            imageView.setImageBitmap(mBitmap);
        }


        final Bitmap finalMBitmap = mBitmap;
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//
//                PrintHelper printHelper = new PrintHelper(getApplicationContext());
//                // Set the desired scale mode.
//                printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
//                // Get the bitmap for the ImageView's drawable.
//                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//                // Print the bitmap.
//                //  PrintAttributes dd=new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_B7).bui
//                printHelper.printBitmap("Print Bitmap", bitmap);
//                finish();
                mSelectedIndex=1;

                Myapp.setBitmap(finalMBitmap);
                PrintTask printTask = new PrintTask();
                printTask.execute();
                finish();

            }
        });







    }

    @Override
    public void onDialogResult(String tag, Intent data) {

    }

    @Override
    public void onConnected(ConnectResult connectResult) {
        if (connectResult == ConnectResult.Success || connectResult == ConnectResult.AlreadyConnected) {

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
        else {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void onDisconnected() {

    }

    private class DrawerTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            //  mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            byte[] data;

            PrinterSetting setting = new PrinterSetting(Printbarcode.this);
            StarIoExt.Emulation emulation = setting.getEmulation();

            boolean doCheckCondition = mSelectedIndex == 1 || mSelectedIndex == 3;

            switch (mSelectedIndex) {
                case 1:
                case 2:
                default:
                    data = CashDrawerFunctions.createData(emulation, ICommandBuilder.PeripheralChannel.No1);
                    break;
                case 3:
                case 4:
                    data = CashDrawerFunctions.createData(emulation, ICommandBuilder.PeripheralChannel.No2);
                    break;
            }

            if (doCheckCondition) {
                Communication.sendCommands(mStarIoExtManager, data, mStarIoExtManager.getPort(), mCallback);
            }
            else {
                Communication.sendCommandsDoNotCheckCondition(mStarIoExtManager, data, mStarIoExtManager.getPort(), mCallback);
            }

            return null;
        }

        private Communication.SendCallback mCallback = new Communication.SendCallback() {
            @Override
            public void onStatus(boolean result, Communication.Result communicateResult) {



                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }

                String msg;

                switch (communicateResult) {
                    case Success:
                        msg = "Success!";
                        break;
                    case ErrorOpenPort:
                        msg = "Fail to openPort";
                        break;
                    case ErrorBeginCheckedBlock:
                        msg = "Printer is offline (beginCheckedBlock)";
                        break;
                    case ErrorEndCheckedBlock:
                        msg = "Printer is offline (endCheckedBlock)";
                        break;
                    case ErrorReadPort:
                        msg = "Read port error (readPort)";
                        break;
                    case ErrorWritePort:
                        msg = "Write port error (writePort)";
                        break;
                    default:
                        msg = "Unknown error";
                        break;
                }

                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

            }
        };
    }
    private class PrintTask extends AsyncTask<Void, Void, Communication.Result> {
        @Override
        protected void onPreExecute() {
            //   mProgressDialog.show();
        }

        @Override
        protected Communication.Result doInBackground(Void... params) {
            byte[] data;

            PrinterSetting setting = new PrinterSetting(getApplicationContext());
            StarIoExt.Emulation emulation = setting.getEmulation();

            ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, mPaperSize);

            switch (mSelectedIndex) {
                default:
                case 1:
                    data = PrinterFunctions.createTextReceiptData(emulation, localizeReceipts, false);
                    break;
                case 2:
                    data = PrinterFunctions.createTextReceiptData(emulation, localizeReceipts, true);
                    break;
                case 3:
                    data = PrinterFunctions.createRasterReceiptData(emulation, localizeReceipts, getResources());
                    break;
                case 4:
                    data = PrinterFunctions.createScaleRasterReceiptData(emulation, localizeReceipts, getResources(), mPaperSize, true);
                    break;
                case 5:
                    data = PrinterFunctions.createScaleRasterReceiptData(emulation, localizeReceipts, getResources(), mPaperSize, false);
                    break;
                case 6:
                    data = PrinterFunctions.createCouponData(emulation, localizeReceipts, getResources(), mPaperSize, ICommandBuilder.BitmapConverterRotation.Normal);
                    break;
                case 7:
                    data = PrinterFunctions.createCouponData(emulation, localizeReceipts, getResources(), mPaperSize, ICommandBuilder.BitmapConverterRotation.Right90);
                    break;
            }

            Communication.sendCommands(mStarIoExtManager, data, mStarIoExtManager.getPort(), mCallback);     // 10000mS!!!

            return null;
        }

        private Communication.SendCallback mCallback = new Communication.SendCallback() {
            @Override
            public void onStatus(boolean result, Communication.Result communicateResult) {


                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }

                String msg;

                switch (communicateResult) {
                    case Success:
                        msg = "Success!";
                        break;
                    case ErrorOpenPort:
                        msg = "Fail to openPort";
                        break;
                    case ErrorBeginCheckedBlock:
                        msg = "Printer is offline (beginCheckedBlock)";
                        break;
                    case ErrorEndCheckedBlock:
                        msg = "Printer is offline (endCheckedBlock)";
                        break;
                    case ErrorReadPort:
                        msg = "Read port error (readPort)";
                        break;
                    case ErrorWritePort:
                        msg = "Write port error (writePort)";
                        break;
                    default:
                        msg = "Unknown error";
                        break;
                }

                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                Myapp.setBitmap(null);
            }
        };
    }
    private final StarIoExtManagerListener mStarIoExtManagerListener = new StarIoExtManagerListener() {
        @Override
        public void onPrinterImpossible() {
            Toast.makeText(getApplicationContext(),"Printer Impossible.",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCashDrawerOpen() {

            Toast.makeText(getApplicationContext(),"Cash Drawer Open.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCashDrawerClose() {

            Toast.makeText(getApplicationContext(),"Cash Drawer Close.",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onAccessoryConnectSuccess() {

            Toast.makeText(getApplicationContext(),"Accessory Connect Success.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAccessoryConnectFailure() {

            Toast.makeText(getApplicationContext(),"Accessory Connect Failure.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAccessoryDisconnect() {

            Toast.makeText(getApplicationContext(),"Accessory Connect Disconnect.",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mStarIoExtManager.setListener(mStarIoExtManagerListener);
        mStarIoExtManager.connect(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mStarIoExtManager.disconnect(this);

    }
}
