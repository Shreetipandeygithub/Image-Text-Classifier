package com.shreeti.snaptext

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.shreeti.snaptext.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()


        //for sign in if account already exist
        binding.txtAlreadyRegistered.setOnClickListener{
            val intent=Intent(this,LoginPage::class.java)
            startActivity(intent)
            finish()
        }


        //for registration of new account
        binding.SignUpNewAccount.setOnClickListener{
            val email=binding.RegisterEmail.text.toString()
            val pass=binding.RegisterPassword.text.toString()
            val passRe=binding.RegisterRePassword.text.toString()

            if(email.isNotEmpty()&& pass.isNotEmpty() && passRe.isNotEmpty()){
                if(pass==passRe){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent=Intent(this,SignUp::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this,"password is not matching",Toast.LENGTH_SHORT).show()

                }
            }else{
                Toast.makeText(this,"Empty Fields are not allowed",Toast.LENGTH_SHORT).show()
            }

        }


    }
}