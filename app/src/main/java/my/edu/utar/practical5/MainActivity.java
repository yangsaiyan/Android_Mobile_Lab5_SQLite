package my.edu.utar.practical5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText etPetName, etPetType, etPetNameDel;
    Button btnAddPet, btnShowPets, btnDeletePet;
    TextView tvPets;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPetName = findViewById(R.id.etPetName);
        etPetType = findViewById(R.id.etPetType);
        etPetNameDel = findViewById(R.id.etPetNameDel);
        btnAddPet = findViewById(R.id.btnAddPet);
        btnShowPets = findViewById(R.id.btnShowPets);
        btnDeletePet = findViewById(R.id.btnDeletePet);
        tvPets = findViewById(R.id.tvPets);

        dbHandler = new DBHandler(this);

        // Add Pet Button Click
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etPetName.getText().toString();
                String type = etPetType.getText().toString();

                if (!name.isEmpty() && !type.isEmpty()) {
                    PetDAO newPet = new PetDAO(name, type);
                    dbHandler.addPet(newPet);
                    etPetName.setText("");
                    etPetType.setText("");
                    tvPets.setText("Pet Added: " + name);
                } else {
                    tvPets.setText("Please enter name & type!");
                }
            }
        });

        // Show Pets Button Click
        btnShowPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PetDAO> petList = dbHandler.getAllPet();
                StringBuilder sb = new StringBuilder();

                for (PetDAO pet : petList) {
                    sb.append("ID: ").append(pet.getId())
                            .append(", Name: ").append(pet.getPetName())
                            .append(", Type: ").append(pet.getPetType())
                            .append("\n");
                }

                if (sb.length() > 0) {
                    tvPets.setText(sb.toString());
                } else {
                    tvPets.setText("No pets found.");
                }
            }
        });

        btnDeletePet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etPetNameDel.getText().toString();
                    dbHandler.deletePet(name);
                    etPetNameDel.setText("");

                    List<PetDAO> petList = dbHandler.getAllPet();
                    StringBuilder sb = new StringBuilder();

                    for (PetDAO pet : petList) {
                        sb.append("ID: ").append(pet.getId())
                                .append(", Name: ").append(pet.getPetName())
                                .append(", Type: ").append(pet.getPetType())
                                .append("\n");
                    }

                    if (sb.length() > 0) {
                        tvPets.setText(sb.toString());
                    } else {
                        tvPets.setText("No pets found.");
                    }
                }
            });
    }
}