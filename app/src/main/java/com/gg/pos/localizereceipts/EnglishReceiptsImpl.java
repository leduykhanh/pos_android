package com.gg.pos.localizereceipts;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

import com.gg.pos.Myapp;
import com.gg.pos.PrinterSetting;
import com.gg.pos.R;
import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.ICommandBuilder.AlignmentPosition;
import com.starmicronics.starioextension.ICommandBuilder.BarcodeSymbology;
import com.starmicronics.starioextension.ICommandBuilder.BarcodeWidth;
import com.starmicronics.starioextension.ICommandBuilder.CodePageType;
import com.starmicronics.starioextension.ICommandBuilder.InternationalType;
import com.starmicronics.starioextension.StarIoExt.CharacterCode;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class EnglishReceiptsImpl extends ILocalizeReceipts {

    public EnglishReceiptsImpl() {
        mLanguageCode = "En";

        mCharacterCode = CharacterCode.Standard;
    }

    @Override
    public void append2inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;
        String data = "";

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            encoding = Charset.forName("US-ASCII");

            builder.appendCodePage(CodePageType.CP998);
        }
//        if (Myapp.getBitmap()==null) {
            for (int i = 0; i < Myapp.getArrayList().size(); i++) {
                data = data +//Myapp.getArrayList().get(i).getDescription()+"\n"+
                        String.format("%6s   %3s    %5s    %6s\n",Myapp.getArrayList().get(i).getSellitemis(),Myapp.getArrayList().get(i).getSellqty()
                        ,Myapp.getArrayList().get(i).getSelling_price(),Myapp.getArrayList().get(i).getSellamount());
//                        Myapp.getArrayList().get(i).getSellitemis() + "   " +
//                        Myapp.getArrayList().get(i).getSellqty() + "    " +
//                        Myapp.getArrayList().get(i).getSelling_price() + "    " +
//                        Myapp.getArrayList().get(i).getSellamount() + "\n";


            }
//        }
        Bitmap icon = BitmapFactory.decodeResource(Myapp.getAppContext().getResources(),
                R.drawable.logo);

        builder.appendInternational(InternationalType.USA);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);
//        if (Myapp.getBitmap()==null) {
            builder.appendBitmap(icon, false);
            builder.append((
                    "ADVANCE LAP PTE. LTD.\n" +
                            "5 Stadium Walk #01-54\n" +
                            "Leisure Park Kallang\n" +
                            "Singapore-397693\n"+
                            "Phone: 8522 6255\n"+
                            "GST / Co. Reg No:201216258G\n"+
                            "Email:sales@advancelap.com\n"+
                            "\n").getBytes(encoding));

            builder.appendAlignment(AlignmentPosition.Left);

            builder.append((
                    "Date:  MM/DD/YYYY    Time:HH:MM PM\n" +
                            "--------------------------------\n" +
                            "\n").getBytes(encoding));

            builder.append((
                    "ItemId   Qty    Price    Amount\n" +
                            "--------------------------------\n" +
                            data+
                            "--------------------------------\n" +
                            "              "+Myapp.getTotal()+"\n"+
                            "      "+Myapp.getGst()+"\n"+
                            "--------------------------------\n"+
//                            "Tender     :\n"+
//                            "Balance    :\n\n"+
//                            "CASH       :\n"+
//                            "--------------------------------\n"+
                            "GOODS PURCHASED IS NONREFUNDABLE"
            ).getBytes(encoding));

//            builder.append(("Total     ").getBytes(encoding));
//
//            builder.appendMultiple(("   $156.95\n").getBytes(encoding), 2, 2);
//
//            builder.append((
//                    "--------------------------------\n" +
//                            "\n" +
//                            "Charge\n" +
//                            "156.95\n" +
//                            "Visa XXXX-XXXX-XXXX-0123\n" +
//                            "\n").getBytes(encoding));
//
//            builder.appendInvert(("Refunds and Exchanges\n").getBytes(encoding));
//
//            builder.append(("Within ").getBytes(encoding));
//
//            builder.appendUnderLine(("30 days").getBytes(encoding));
//
//            builder.append((" with receipt\n").getBytes(encoding));
//
//            builder.append((
//                    "And tags attached\n" +
//                            "\n").getBytes(encoding));
//        }else {
//            builder.appendBitmap(Myapp.getBitmap(),false);
//        }


        builder.appendAlignment(AlignmentPosition.Center);

//        builder.appendBarcode(("{BStar.").getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public void append3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            encoding = Charset.forName("US-ASCII");

            builder.appendCodePage(CodePageType.CP998);
        }

        builder.appendInternational(InternationalType.USA);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);
        builder.appendBitmap(Myapp.getBitmap(), false);

//        builder.append((
//                "Star Clothing Boutique\n" +
//                        "123 Star Road\n" +
//                        "City, State 12345\n" +
//                        "\n").getBytes(encoding));
//
//        builder.appendAlignment(AlignmentPosition.Left);
//
//        builder.append((
//                "Date:MM/DD/YYYY                    Time:HH:MM PM\n" +
//                        "------------------------------------------------\n" +
//                        "\n").getBytes(encoding));
//
//        builder.appendEmphasis(("SALE\n").getBytes(encoding));
//
//        builder.append((
//                "SKU               Description              Total\n" +
//                        "300678566         PLAIN T-SHIRT            10.99\n" +
//                        "300692003         BLACK DENIM              29.99\n" +
//                        "300651148         BLUE DENIM               29.99\n" +
//                        "300642980         STRIPED DRESS            49.99\n" +
//                        "300638471         BLACK BOOTS              35.99\n" +
//                        "\n" +
//                        "Subtotal                                  156.95\n" +
//                        "Tax                                         0.00\n" +
//                        "------------------------------------------------\n").getBytes(encoding));
//
//        builder.append(("Total                       ").getBytes(encoding));
//
//        builder.appendMultiple(("   $156.95\n").getBytes(encoding), 2, 2);
//
//        builder.append((
//                "------------------------------------------------\n" +
//                        "\n" +
//                        "Charge\n" +
//                        "156.95\n" +
//                        "Visa XXXX-XXXX-XXXX-0123\n" +
//                        "\n").getBytes(encoding));
//
//        builder.appendInvert(("Refunds and Exchanges\n").getBytes(encoding));
//
//        builder.append(("Within ").getBytes(encoding));
//
//        builder.appendUnderLine(("30 days").getBytes(encoding));
//
//        builder.append((" with receipt\n").getBytes(encoding));
//
//        builder.append((
//                "And tags attached\n" +
//                        "\n").getBytes(encoding));
//
//        builder.appendAlignment(AlignmentPosition.Center);

//        builder.appendBarcode(("{BStar.").getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public void append4inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            encoding = Charset.forName("US-ASCII");

            builder.appendCodePage(CodePageType.CP998);
        }

        builder.appendInternational(InternationalType.USA);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "Star Clothing Boutique\n" +
                        "123 Star Road\n" +
                        "City, State 12345\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "Date:MM/DD/YYYY                    aaaaa                     Time:HH:MM PM\n" +
                        "---------------------------------------------------------------------\n" +
                        "\n").getBytes(encoding));

        builder.appendEmphasis(("SALE\n").getBytes(encoding));

        builder.append((
                "SKU                        Description                          Total\n" +
                        "300678566                  PLAIN T-SHIRT                        10.99\n" +
                        "300692003                  BLACK DENIM                          29.99\n" +
                        "300651148                  BLUE DENIM                           29.99\n" +
                        "300642980                  STRIPED DRESS                        49.99\n" +
                        "300638471                  BLACK BOOTS                          35.99\n" +
                        "\n" +
                        "Subtotal                                                       156.95\n" +
                        "Tax                                                              0.00\n" +
                        "---------------------------------------------------------------------\n").getBytes(encoding));

        builder.append(("Total                                            ").getBytes(encoding));

        builder.appendMultiple(("   $156.95\n").getBytes(encoding), 2, 2);

        builder.append((
                "---------------------------------------------------------------------\n" +
                        "\n" +
                        "Charge\n" +
                        "156.95\n" +
                        "Visa XXXX-XXXX-XXXX-0123\n" +
                        "\n").getBytes(encoding));

        builder.appendInvert(("Refunds and Exchanges\n").getBytes(encoding));

        builder.append(("Within ").getBytes(encoding));

        builder.appendUnderLine(("30 days").getBytes(encoding));

        builder.append((" with receipt\n").getBytes(encoding));

        builder.append((
                "And tags attached\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendBarcode(("{BStar.").getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public Bitmap create2inchRasterReceiptImage() {
        String textToPrint =
                "   Star Clothing Boutique\n" +
                "        123 Star Road\n" +
                "      City, State 12345\n" +
                "ccccccccccc\n" +
                "Date:MM/DD/YYYY Time:HH:MM PM\n" +
                "-----------------------------\n" +
                "SALE\n" +
                "SKU       Description   Total\n" +
                "300678566 PLAIN T-SHIRT 10.99\n" +
                "300692003 BLACK DENIM   29.99\n" +
                "300651148 BLUE DENIM    29.99\n" +
                "300642980 STRIPED DRESS 49.99\n" +
                "30063847  BLACK BOOTS   35.99\n" +
                "\n" +
                "Subtotal               156.95\n" +
                "Tax                      0.00\n" +
                "-----------------------------\n" +
                "Total                 $156.95\n" +
                "-----------------------------\n" +
                "\n" +
                "Charge\n" +
                "156.95\n" +
                "Visa XXXX-XXXX-XXXX-0123\n" +
                "Refunds and Exchanges\n" +
                "Within 30 days with receipt\n" +
                "And tags attached\n";

        int      textSize = 22;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSetting.PAPER_SIZE_TWO_INCH, typeface);
    }

    @Override
    public Bitmap create3inchRasterReceiptImage() {
        String textToPrint =
                "        Star Clothing Boutique\n" +
                "             123 Star Road\n" +
                "           City, State 12345\n" +
                "\n" +
                "Date:MM/DD/YYYY     YYYYa     Time:HH:MM PM\n" +
                "--------------------------------------\n" +
                "SALE\n" +
                "SKU            Description       Total\n" +
                "300678566      PLAIN T-SHIRT     10.99\n" +
                "300692003      BLACK DENIM       29.99\n" +
                "300651148      BLUE DENIM        29.99\n" +
                "300642980      STRIPED DRESS     49.99\n" +
                "30063847       BLACK BOOTS       35.99\n" +
                "\n" +
                "Subtotal                        156.95\n" +
                "Tax                               0.00\n" +
                "--------------------------------------\n" +
                "Total                          $156.95\n" +
                "--------------------------------------\n" +
                "\n" +
                "Charge\n" +
                "156.95\n" +
                "Visa XXXX-XXXX-XXXX-0123\n" +
                "Refunds and Exchanges\n" +
                "Within 30 days with receipt\n" +
                "And tags attached\n";

        int      textSize = 25;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSetting.PAPER_SIZE_THREE_INCH, typeface);
    }

    @Override
    public Bitmap create4inchRasterReceiptImage() {
        String textToPrint =
                "                   Star Clothing Boutique\n" +
                "                        123 Star Road\n" +
                "                      City, State 12345\n" +
                "\n" +
                "Date:MM/DD/YYYY      zzzzzzzz                       Time:HH:MM PM\n" +
                "---------------------------------------------------------\n" +
                "SALE\n" +
                "SKU                     Description                 Total\n" +
                "300678566               PLAIN T-SHIRT               10.99\n" +
                "300692003               BLACK DENIM                 29.99\n" +
                "300651148               BLUE DENIM                  29.99\n" +
                "300642980               STRIPED DRESS               49.99\n" +
                "300638471               BLACK BOOTS                 35.99\n" +
                "\n" +
                "Subtotal                                           156.95\n" +
                "Tax                                                  0.00\n" +
                "---------------------------------------------------------\n" +
                "Total                                             $156.95\n" +
                "---------------------------------------------------------\n" +
                "\n" +
                "Charge\n" +
                "156.95\n" +
                "Visa XXXX-XXXX-XXXX-0123\n" +
                "Refunds and Exchanges\n" +
                "Within 30 days with receipt\n" +
                "And tags attached\n";

        int      textSize = 23;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSetting.PAPER_SIZE_FOUR_INCH, typeface);
    }

    @Override
    public Bitmap createCouponImage(Resources resources) {
        return BitmapFactory.decodeResource(resources, R.drawable.coupon_image);
    }

    @Override
    public Bitmap createEscPos3inchRasterReceiptImage() {
        String textToPrint =
                "\n" +
                "      Star Clothing Boutique\n" +
                "           123 Star Road\n" +
                "         City, State 12345\n" +
                "\n" +
                "Date:MM/DD/YYYY    fffffffff   Time:HH:MM PM\n" +
                "-----------------------------------\n" +
                "SALE\n" +
                "SKU          Description      Total\n" +
                "300678566    PLAIN T-SHIRT    10.99\n" +
                "300692003    BLACK DENIM      29.99\n" +
                "300651148    BLUE DENIM       29.99\n" +
                "300642980    STRIPED DRESS    49.99\n" +
                "30063847     BLACK BOOTS      35.99\n" +
                "\n" +
                "Subtotal                     156.95\n" +
                "Tax                            0.00\n" +
                "-----------------------------------\n" +
                "Total                       $156.95\n" +
                "-----------------------------------\n" +
                "\n" +
                "Charge\n" +
                "156.95\n" +
                "Visa XXXX-XXXX-XXXX-0123\n" +
                "Refunds and Exchanges\n" +
                "Within 30 days with receipt\n" +
                "And tags attached\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSetting.PAPER_SIZE_ESCPOS_THREE_INCH, typeface);
    }

    @Override
    public void appendEscPos3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            encoding = Charset.forName("US-ASCII");

            builder.appendCodePage(CodePageType.CP998);
        }

        builder.appendInternational(InternationalType.USA);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);
        builder.appendBitmap(Myapp.getBitmap(),false);
//        builder.append((
//                "\n" +
//                "Star Clothing Boutique\n" +
//                        "123 Star Road\n" +
//                        "City, State 12345\n" +
//                        "\n").getBytes(encoding));
//
//        builder.appendAlignment(AlignmentPosition.Left);
//
//        builder.append((
//                "Date:MM/DD/YYYY      ttttttt        Time:HH:MM PM\n" +
//                "------------------------------------------\n" +
//                "\n").getBytes(encoding));
//
//        builder.appendEmphasis(("SALE \n").getBytes(encoding));
//
//        builder.append((
//                "SKU            Description           Total\n" +
//                        "300678566      PLAIN T-SHIRT         10.99\n" +
//                        "300692003      BLACK DENIM           29.99\n" +
//                        "300651148      BLUE DENIM            29.99\n" +
//                        "300642980      STRIPED DRESS         49.99\n" +
//                        "300638471      BLACK BOOTS           35.99\n" +
//                        "\n" +
//                        "Subtotal                            156.95\n" +
//                        "Tax                                   0.00\n" +
//                        "------------------------------------------\n").getBytes(encoding));
//
//        builder.append(("Total                 ").getBytes(encoding));
//
//        builder.appendMultiple(("   $156.95\n").getBytes(encoding), 2, 2);
//
//        builder.append((
//                "------------------------------------------\n" +
//                        "\n" +
//                        "Charge\n" +
//                        "156.95\n" +
//                        "Visa XXXX-XXXX-XXXX-0123\n" +
//                        "\n").getBytes(encoding));
//
//        builder.appendInvert(("Refunds and Exchanges\n").getBytes(encoding));
//
//        builder.append(("Within ").getBytes(encoding));
//
//        builder.appendUnderLine(("30 days").getBytes(encoding));
//
//        builder.append((" with receipt\n").getBytes(encoding));
//
//        builder.append((
//                "And tags attached\n" +
//                        "\n").getBytes(encoding));
//
//        builder.appendAlignment(AlignmentPosition.Center);
//
//        builder.appendBarcode(("{BStar.").getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public void appendDotImpact3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            encoding = Charset.forName("US-ASCII");

            builder.appendCodePage(CodePageType.CP998);
        }

        builder.appendInternational(InternationalType.USA);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "Star Clothing Boutique\n" +
                        "123 Star Road\n" +
                        "City, State 12345\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "Date:MM/DD/YYYY    jjjjjj          Time:HH:MM PM\n" +
                        "------------------------------------------\n" +
                        "\n").getBytes(encoding));

        builder.appendEmphasis(("SALE \n").getBytes(encoding));

        builder.append((
                "SKU             Description          Total\n" +
                        "300678566       PLAIN T-SHIRT        10.99\n" +
                        "300692003       BLACK DENIM          29.99\n" +
                        "300651148       BLUE DENIM           29.99\n" +
                        "300642980       STRIPED DRESS        49.99\n" +
                        "300638471       BLACK BOOTS          35.99\n" +
                        "\n" +
                        "Subtotal                            156.95\n" +
                        "Tax                                   0.00\n" +
                        "------------------------------------------\n" +
                        "Total                              $156.95\n" +
                        "------------------------------------------\n" +
                        "\n" +
                        "Charge\n" +
                        "156.95\n" +
                        "Visa XXXX-XXXX-XXXX-0123\n" +
                        "\n").getBytes(encoding));

        builder.appendInvert(("Refunds and Exchanges\n").getBytes(encoding));

        builder.append(("Within ").getBytes(encoding));

        builder.appendUnderLine(("30 days").getBytes(encoding));

        builder.append((" with receipt\n").getBytes(encoding));
    }

    @Override
    public void appendTextLabelData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            encoding = Charset.forName("US-ASCII");

            builder.appendCodePage(CodePageType.CP998);
        }

        builder.appendInternational(InternationalType.USA);

        builder.appendCharacterSpace(0);

        builder.appendUnitFeed(20 * 2);

        builder.appendMultipleHeight(2);

        builder.append(("Star Micronics America, Inc.").getBytes(encoding));

        builder.appendUnitFeed(64);

        builder.append(("65 Clyde Road Suite G").getBytes(encoding));

        builder.appendUnitFeed(64);

        builder.append(("Somerset, NJ 08873-9997 U.S.A").getBytes(encoding));

        builder.appendUnitFeed(64);

        builder.appendMultipleHeight(1);
    }

    @Override
    public String createPasteTextLabelString() {
        return "Star Micronics America, Inc.\n" +
               "65 Clyde Road Suite G\n" +
               "Somerset, NJ 08873-9997 U.S.A";
    }

    @Override
    public void appendPasteTextLabelData(ICommandBuilder builder, String pasteText, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            encoding = Charset.forName("US-ASCII");

            builder.appendCodePage(CodePageType.CP998);
        }

        builder.appendInternational(InternationalType.USA);

        builder.appendCharacterSpace(0);

        builder.append(pasteText.getBytes(encoding));
    }
}
