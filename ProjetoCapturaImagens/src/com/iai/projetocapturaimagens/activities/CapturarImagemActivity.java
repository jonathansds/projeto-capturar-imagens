package com.iai.projetocapturaimagens.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.iai.projetocapturaimagens.R;

/**
 * @author Jonathan
 * @version 23/08/2014
 * @project ProjetoCapturaImagens
 */
public class CapturarImagemActivity extends Activity implements OnClickListener{

	/** RESULT_CAMERA */
	private static final int RESULT_CAMERA = 111;

	/** RESULT_GALERIA */
	private static final int RESULT_GALERIA = 222;
	
    private Button botaoAddGaleria;
    private Button botaoAddCamera;
    private ImageView imagemCapturada;
    private Button botaoDeletarImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_capturar_imagens);
        
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        imagemCapturada = (ImageView) findViewById(R.id.foto);
        
        botaoAddCamera = (Button) findViewById(R.id.botao_capturar_camera);
        botaoAddCamera.setOnClickListener(this);

        botaoAddGaleria = (Button) findViewById(R.id.botao_capturar_galeria);
        botaoAddGaleria.setOnClickListener(this);

        botaoDeletarImagem = (Button) findViewById(R.id.botao_deletar);
        botaoDeletarImagem.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == RESULT_CAMERA && resultCode == RESULT_OK){
            Bitmap foto = (Bitmap)data.getExtras().get("data");
            imagemCapturada.setImageBitmap(foto);
        }else if(requestCode == RESULT_GALERIA && resultCode == RESULT_OK){
        	Uri imageUri = data.getData();
        	String[] colunaArquivo = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(imageUri, colunaArquivo, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(colunaArquivo[0]);
            String picturePath = cursor.getString(columnIndex);
            
            Bitmap foto = BitmapFactory.decodeFile(picturePath.toString());
            
            if(foto != null){
            	imagemCapturada.setImageBitmap(foto);
            }
        }
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent;
		
		if(id == R.id.botao_capturar_camera){
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE );
            startActivityForResult(intent, RESULT_CAMERA);
            
		}else if (id == R.id.botao_capturar_galeria){
			intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, RESULT_GALERIA);
			
		}else if (id == R.id.botao_deletar){
			imagemCapturada.setImageBitmap(null);
		}
	}
}
