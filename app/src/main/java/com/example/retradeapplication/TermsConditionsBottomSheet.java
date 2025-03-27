package com.example.retradeapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TermsConditionsBottomSheet extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.terms_conditions_bottom_sheet, container, false);

        // Set Terms & Conditions Content
        TextView termsContent = view.findViewById(R.id.terms_content);

        String termsAndConditions = "1. Acceptance of Terms\nBy accessing or using the ReTrade app, you agree to comply with these Terms and Conditions. If you do not agree, please do not use the app.\n\n" +
                "2. User Registration\nTo access certain features, you must create an account with accurate and complete information. You are responsible for maintaining the confidentiality of your account credentials.\n\n" +
                "3. Age Restriction\nYou must be at least 18 years old to use ReTrade. If you are under 18, parental consent is required.\n\n" +
                "4. Allowed Transactions\nReTrade is a marketplace for buying and selling products. Illegal, counterfeit, or prohibited items must not be listed. ReTrade reserves the right to remove any such listings.\n\n" +
                "5. User Responsibilities\nUsers must ensure that the products they list are legal, genuine, and accurately described.\nUsers must communicate respectfully and avoid fraudulent activities.\nAny disputes between buyers and sellers must be handled directly between them.\n\n" +
                "6. Payment & Transactions\nReTrade does not process payments directly. All transactions must be handled through the agreed-upon method between buyers and sellers.\nUsers are responsible for verifying the credibility of other users before making a purchase.\n\n" +
                "7. Prohibited Content & Activities\nNo posting of offensive, illegal, or inappropriate content.\nNo spam, scams, or misleading advertisements.\nNo harassment or abusive behavior towards other users.\n\n" +
                "8. Account Suspension or Termination\nReTrade reserves the right to suspend or terminate any account that violates these Terms and Conditions without prior notice.\n\n" +
                "9. Privacy Policy\nBy using ReTrade, you agree to our Privacy Policy, which explains how we collect, store, and use your personal data.\n\n" +
                "10. Liability Disclaimer\nReTrade is not responsible for any losses, damages, or disputes arising from transactions made between users. Use the app at your own risk.\n\n" +
                "11. Modification of Terms\nReTrade may update these Terms at any time. Users will be notified of major changes, and continued use of the app means acceptance of the updated Terms.";

        termsContent.setText(termsAndConditions);

        // Close Button Click Event
        Button closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> dismiss());

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BottomSheetDialog(requireContext(), getTheme());
    }
}