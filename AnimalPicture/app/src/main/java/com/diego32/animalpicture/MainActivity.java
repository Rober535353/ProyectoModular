package com.diego32.animalpicture;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.diego32.animalpicture.ml.ModelAnimalPicture;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    private EditText multiLine;
    int imageSize=200;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        multiLine = findViewById(R.id.editTextTextMultiLine);
    }public void tomarFoto(View view){
        if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,3);
        }
        else{
            requestPermissions(new String[]{Manifest.permission.CAMERA},100);
        }
    }
    public void galeria(View view){
        Intent intent=new Intent();
        intent.setType("image/");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent,"Seleccione una imágen"),1
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==3){
                Bundle extras=data.getExtras();
                Bitmap image=(Bitmap) extras.get("data");

                int dimension=Math.min(image.getWidth(),image.getHeight());
                image= ThumbnailUtils.extractThumbnail(image,dimension,dimension);
                imageView.setImageBitmap(image);

                image=Bitmap.createScaledBitmap(image,imageSize,imageSize,false);
                clasificarImagen(image);
            }
            else{
                Uri selectedImageUri=null;
                Uri selectedImage;
                String filePath=null;
                switch (requestCode){
                    case 1:
                        if(resultCode==Activity.RESULT_OK){
                            selectedImage=data.getData();
                            String selectedPath=selectedImage.getPath();
                            if(selectedPath!=null){
                                InputStream imageStream=null;
                                try {
                                    imageStream=getContentResolver().openInputStream(selectedImage);
                                }
                                catch (FileNotFoundException e){
                                    multiLine.setText(e.getMessage());
                                    e.printStackTrace();
                                }
                                Bitmap bitmap= BitmapFactory.decodeStream(imageStream);
                                imageView.setImageBitmap(bitmap);

                                Bitmap image=Bitmap.createScaledBitmap(bitmap,imageSize,imageSize,false);
                                clasificarImagen(image);
                            }
                        }
                        break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void clasificarImagen(Bitmap image) {
        try {
            ModelAnimalPicture model = ModelAnimalPicture.newInstance(getApplicationContext());

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 200, 200, 3}, DataType.FLOAT32);
            //4 por el número de bytes del float
            //3 por el RGB
            ByteBuffer byteBuffer=ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());
            inputFeature0.loadBuffer(byteBuffer);

            int[] intValues=new int[imageSize*imageSize];
            image.getPixels(intValues,0,image.getWidth(),0,0,image.getWidth(), image.getHeight());
            int pixel=0;
            for(int i=0;i<imageSize;i++){
                for(int j=0;j<imageSize;j++){
                    //obtenemos los valores de 255 a 0 RGB
                    int val=intValues[pixel++];//RGB
                    byteBuffer.putFloat(((val>>16)&0xFF)*(1.f/1));
                    byteBuffer.putFloat(((val>>8)&0xFF)*(1.f/1));
                    byteBuffer.putFloat((val&0xFF)*(1.f/1));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            ModelAnimalPicture.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences=outputFeature0.getFloatArray();
            int maxPos=0;
            float maxConfidence=0;
            for(int i=0;i<confidences.length;i++){
                if(confidences[i]>maxConfidence){
                    maxConfidence=confidences[i];
                    maxPos=i;
                }
            }
            String[] classes={"aguila","araña","ballena","borrego","caballo","cabipa","cabra","cangrejo","cebra","cerdo"};
            textView.setText(classes[maxPos]);

            model.close();
        }catch (IOException e){

        }
    }
}