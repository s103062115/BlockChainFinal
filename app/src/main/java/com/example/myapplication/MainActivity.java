package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public static final int CROP_PHOTO = 2;
    public static final int REQUEST_CODE_SIGN_IN = 3;
    private ImageView showImage;
    EthGetTransactionCount ethGetTransactionCount = null;
    Drive googleDriveService;
    public static String link = "";
    //圖片路徑
    private Uri imageUri;

    //圖片名稱
    private String filename;
    private TextView lbl_imgpath;

    private  Credentials credentials;
    private String CONTRACT_ADDRESS;
    private String ACCOUNT_ADDRESS;
    private Admin web3j;
    DriveServiceHelper mDriveServiceHelper;
    private FileOutputStream b;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestSignIn();
        credentials = Credentials.create("0xab09158d9a817633c28c74b6e6c1bf34c26ffadc1a961870beaeef38b0753495","0xa1f8553f25bf39e5485fb6ed275685a3c091d34322dbcf142c1de69602a1e8fe9df06ceba9b6dc9f45a38b00176c1c2a158f4265419bcafab6b6dc4e6265b932");
        web3j = Admin.build(new HttpService("http://foodchain-node3.etherhost.org:22003"));
        CONTRACT_ADDRESS = "0xA4fafbE0ea4823e262b4916EF93CC5A6306A5DBc";
        ACCOUNT_ADDRESS = "0x7CbEb723CA0788af6549110fb2a9816ED0BAa1a6";
        FloatingActionButton fab = findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                food3.Food3 f = food3.Food3.load(CONTRACT_ADDRESS,web3j,credentials, BigInteger.valueOf(0),BigInteger.valueOf(10000000));
                TextInputEditText t = findViewById(R.id.item);
                String title = t.getText().toString();
                t = findViewById(R.id.org);
                String org = t.getText().toString();
                String s = f.FoodLog(BigInteger.valueOf(8922094),"123", title, org,"").encodeFunctionCall();

                try {
                    ethGetTransactionCount = web3j.ethGetTransactionCount(
                            ACCOUNT_ADDRESS, DefaultBlockParameterName.PENDING).send();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        String s2 = null;
                        com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
                        fileMetadata.setName("photo.jpg");
                        java.io.File filePath = new java.io.File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()+"/Camera/tmp.jpg");
                        FileContent mediaContent = new FileContent("image/jpeg", filePath);
                        com.google.api.services.drive.model.File file = null;
                        Log.i("upload","begin");
                        try {
                            file = googleDriveService.files().create(fileMetadata, mediaContent)
                                    .setFields("id")
                                    .execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        link = "https://drive.google.com/file/d/"+file.getId();
                        Log.i("URL",link);

                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()+"/Camera/tmp.jpg"));
                            StringBuilder stringBuilder = new StringBuilder();
                            String line = null;
                            String ls = System.getProperty("line.separator");
                            while ((line = reader.readLine()) != null) {
                                stringBuilder.append(line);
                                stringBuilder.append(ls);
                            }
// delete the last new line separator
                            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                            reader.close();

                            String content = stringBuilder.toString();
                            MessageDigest digest = MessageDigest.getInstance("SHA-256");
                            byte[] encodedhash = digest.digest(
                                    content.getBytes(StandardCharsets.UTF_8));
                            String r = bytesToHex(encodedhash);
                            s2 = f.FoodLogImage(BigInteger.valueOf(8922094), link, r).encodeFunctionCall();

                            RawTransaction transaction2 = RawTransaction.createTransaction(ethGetTransactionCount.getTransactionCount().add(BigInteger.valueOf(1)), BigInteger.valueOf(0),BigInteger.valueOf(5000000),  CONTRACT_ADDRESS,s2);
                            byte[] signedMessage2 = TransactionEncoder.signMessage(transaction2, credentials);
                            String hexValue2 = Numeric.toHexString(signedMessage2);

                            EthSendTransaction transactionResponse2 = web3j .ethSendRawTransaction(hexValue2 ).sendAsync().get();
                            String transactionHash2 = transactionResponse2.getTransactionHash();
                            if (transactionResponse2.hasError()) {
                                transactionHash2 = transactionResponse2.getError().getMessage();}
                            Log.i("hash2",transactionHash2);
                            /*TransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor(
                                    web3j,
                                    TransactionManager.DEFAULT_POLLING_FREQUENCY,
                                    TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
                            TransactionReceipt txReceipt = receiptProcessor.waitForTransactionReceipt(transactionHash2);
                            Log.i("receipt2",txReceipt.toString());*/
                        } catch (IOException | NoSuchAlgorithmException /*| TransactionException*/ e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        return link;
                    }
                };




                RawTransaction transaction = RawTransaction.createTransaction(ethGetTransactionCount.getTransactionCount(), BigInteger.valueOf(0),BigInteger.valueOf(5000000),  CONTRACT_ADDRESS,s);

                byte[] signedMessage = TransactionEncoder.signMessage(transaction, credentials);
                String hexValue = Numeric.toHexString(signedMessage);


                try {
                    EthSendTransaction transactionResponse = web3j .ethSendRawTransaction(hexValue ).sendAsync().get();
                    String transactionHash = transactionResponse.getTransactionHash();

                    if (transactionResponse.hasError()) {
                        transactionHash = transactionResponse.getError().getMessage();}
                    Log.i("hash1",transactionHash);
                    /*TransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor(
                            web3j,
                            TransactionManager.DEFAULT_POLLING_FREQUENCY,
                            TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
                    TransactionReceipt txReceipt = receiptProcessor.waitForTransactionReceipt(transactionHash);
                    Log.i("receipt1",txReceipt.toString());*/

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }/* catch (TransactionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/


                t = findViewById(R.id.section);
                String sec = t.getText().toString();
                s = f.FoodLogSection(BigInteger.valueOf(8922094),sec, "123", "456").encodeFunctionCall();
                transaction = RawTransaction.createTransaction(ethGetTransactionCount.getTransactionCount().add(BigInteger.valueOf(2)), BigInteger.valueOf(0),BigInteger.valueOf(5000000),  CONTRACT_ADDRESS,s);

                signedMessage = TransactionEncoder.signMessage(transaction, credentials);
                hexValue = Numeric.toHexString(signedMessage);
                try {
                    EthSendTransaction transactionResponse = web3j .ethSendRawTransaction(hexValue ).sendAsync().get();
                    String transactionHash = transactionResponse.getTransactionHash();

                    if (transactionResponse.hasError()) {
                        transactionHash = transactionResponse.getError().getMessage();}
                    Log.i("hash1",transactionHash);
                    /*TransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor(
                            web3j,
                            TransactionManager.DEFAULT_POLLING_FREQUENCY,
                            TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
                    TransactionReceipt txReceipt = receiptProcessor.waitForTransactionReceipt(transactionHash);
                    Log.i("receipt1",txReceipt.toString());
*/
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } /*catch (TransactionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                task.execute();
                /*try {
                    String str_result = task.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

            }
        });
    }
    public void setLink(){

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            /*if(requestCode == CROP_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);


                try {
                    b = new FileOutputStream(path+filename+".jpg");

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把資料寫入檔案,其中第一個引數表示圖片格式,
                    //第二個引數指壓縮率。100表示不壓縮
                    Log.i("tmp","DONE");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                ImageView ic = (ImageView) findViewById(R.id.imageView);

                ic.setImageBitmap(bitmap);
            }else */if(requestCode == REQUEST_CODE_SIGN_IN) {
                handleSignInResult(data);
            }

        } else finish();
    }
    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    Log.d("1", "Signed in as " + googleAccount.getEmail());

                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    getActivity(), Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("My Application")
                                    .build();

                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions.
                    //mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                })
                .addOnFailureListener(exception -> {
                    Log.e("2", "Unable to sign in.", exception);
                });
    }
    private void requestSignIn() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE),
                                new Scope(DriveScopes.DRIVE_APPDATA))
                        .requestIdToken("462265459092-k5itjh0f7qidj9mvln4crpipn9suc861.apps.googleusercontent.com")
                        .build();
        GoogleSignInClient client = GoogleSignIn.getClient(getActivity(), signInOptions);

        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    private Activity getActivity() {
        return this;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}