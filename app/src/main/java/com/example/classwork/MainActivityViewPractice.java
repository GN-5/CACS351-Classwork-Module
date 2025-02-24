package com.example.classwork;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import com.example.classwork.databinding.ActivityMainViewPracticeBinding;
import com.example.classwork.databinding.ActivityMainViewPracticeRlBinding;
import org.w3c.dom.Text;

public class MainActivityViewPractice extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener{

    private TextView greetingLabel;
    private TextView userName;
    private Button clickMe;
    boolean addExtraCheese = false;
    boolean addExtraToppings = false;

    private ActivityMainViewPracticeRlBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_practice);

        binding = ActivityMainViewPracticeRlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] genders = {Gender.MALE.toString(), Gender.FEMALE.toString(), Gender.OTHERS.toString()};
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()).toPlatformInsets();
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            }
            return insets;
        });

        binding.clickMe.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //listening here
                        if (validateUserName()) {
                            String userName = binding.userNameInput.getText().toString();
                            Toast.makeText(
                                    MainActivityViewPractice.this,
                                    "Your name is : " + userName,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                        Gender selectedGender = (Gender) binding.spinner.getSelectedItem();
                        Toast.makeText(
                                MainActivityViewPractice.this,
                                "Your name is : " + selectedGender,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
        binding.userNameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    binding.userNameTIL.setErrorEnabled(false);
                    binding.userNameTIL.setError("");
                }
            }
        });

        binding.spinner.setAdapter(
                new ArrayAdapter<String>(
                        MainActivityViewPractice.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        genders
                ) {
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            return false;
                        }
                        return super.isEnabled(position);
                    }
                }

        );
        int index = Arrays.asList(genders).indexOf(Gender.OTHERS);


        // unneeded implementation - just for reference - implemented better above /|\
//        binding.addExtraCheese.setOnCheckedChangeListener(
//                new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                        addExtraCheese = isChecked;
//                    }
//                }
//        );
//        binding.addExtratoppings.setOnCheckedChangeListener(
//                new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                        addExtraToppings = isChecked;
//                    }
//                }
//        );



        binding.spinner.setSelection(index);
        binding.spinner.setOnItemSelectedListener(MainActivityViewPractice.this);

        binding.addExtraCheese.setOnCheckedChangeListener(MainActivityViewPractice.this);
        binding.addExtratoppings.setOnCheckedChangeListener(MainActivityViewPractice.this);

    }

    private boolean validateUserName(){
        boolean isValid = true;

        String userName = binding.userNameInput.getText().toString();

        if (userName.length() < 5) {
            isValid = false;
            String userNameError = "Username cannot be less than 5 characters";
            binding.userNameTIL.setErrorEnabled(true);
            binding.userNameTIL.setError(userNameError);

        }
        return isValid;
    }


    private boolean validateSelectedGender(){
        boolean isValid = true;
        String selectedGender = null;

        if (selectedGender == null) {
            isValid = false;
            String validationMessage = "Gender is required";
            new AlertDialog.Builder(MainActivityViewPractice.this)
                    .setTitle(R.string.gender_error)
                    .setMessage(validationMessage)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        }
        return isValid;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(compoundButton.getId() == R.id.addExtraCheese) {
            addExtraCheese = isChecked;
        }
        else if(compoundButton.getId() == R.id.addExtratoppings) {
            addExtraToppings = isChecked;
        }
        Log.d(TAG,"onCheckedChange: addExtraCheese: " + addExtraToppings + " - addExtraToppings -:" + addExtraToppings);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Gender selection = (Gender) adapterView.getItemAtPosition(position);
        Toast.makeText(
                MainActivityViewPractice.this,
                "Selected " + selection,
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //do nothing
    }
}