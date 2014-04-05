package pl.scrum.scrumit;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity {
    private EditText etConverter;
  //  private Button btnFahrenheitToCelsius;
    private Button btnCelsiusToFahrenheit;
    private TextView tvResult;
    
    private static final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String METHOD_CEL_TO_FAHR = "CelsiusToFahrenheit";
    private static final String METHOD_FAHR_TO_CEL = "FahrenheitToCelsius";
    private static final String SOAP_ACTION_CEL_TO_FAHR = "http://tempuri.org/CelsiusToFahrenheit";
    private static final String SOAP_ACTION_FAHR_TO_CEL = "http://tempuri.org/FahrenheitToCelsius";
    
    private OnClickListener CelToFahrListener = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            convertCelToFahr(etConverter.getText().toString());
            Log.d("tag", "dasdas");
        }
    };
  /*  private OnClickListener FahrToCelListener = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            convertFahrToCel(etConverter.getText().toString());
        }
    };
    */
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_second);
        etConverter = (EditText)findViewById(R.id.etConverter);
        btnCelsiusToFahrenheit = (Button)findViewById(R.id.btnCelsiusToFahrenheit);
   //     btnFahrenheitToCelsius = (Button)findViewById(R.id.btnFahrenheitToCelsius);
   //     tvResult = (TextView)findViewById(R.id.tvResult);
        btnCelsiusToFahrenheit.setOnClickListener(CelToFahrListener);
   //     btnFahrenheitToCelsius.setOnClickListener(FahrToCelListener);      
    }
    
    private void convertCelToFahr(String val) {
        String result = makeCallToWebService(SOAP_ACTION_CEL_TO_FAHR, 
                                             METHOD_CEL_TO_FAHR, 
                                             "Celsius", val);
        tvResult.setText(etConverter.getText() + " Celsius degree = " 
                         + result + " Fahrenheit degree");
    }
    
    private void convertFahrToCel(String val) {
        String result = makeCallToWebService(SOAP_ACTION_FAHR_TO_CEL, 
                                             METHOD_FAHR_TO_CEL, 
                                             "Fahrenheit", val);
        tvResult.setText(etConverter.getText() + " Fahrenheit degree = " 
                         + result + " Celsius degree");
    }
    
    private String makeCallToWebService(String soapAction, String method, 
                                        String attribute, String val) {
        //Make request
        SoapObject request = new SoapObject(NAMESPACE, method);
        request.addProperty(attribute, val);
        //Make SOAP envelope
        SoapSerializationEnvelope envelope = 
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        //Send request
        AndroidHttpTransport aht = new AndroidHttpTransport(URL);
        try {
            aht.call(soapAction, envelope);
            SoapPrimitive result = (SoapPrimitive)envelope.getResponse();
            return result.toString();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), 
                           e.toString(), 
                           Toast.LENGTH_LONG).show();
            return null;
        }
    }
}





