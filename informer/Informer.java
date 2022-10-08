package informer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import forwarderReceiver.Service;
import models.Candidato;

public class Informer extends Service {
    public static boolean startSendingInfo = false;

    public String createJsonStr() {
        Candidato candidato = Candidato.getNullInstance();
        Candidato[] candidatos = (Candidato[]) candidato.getAll();

        String jsonStr = "{";
        for (int i = 0; i < candidatos.length; i++) {
            candidato = candidatos[i];
            if (i != candidatos.length - 1)
                jsonStr += "\"" + candidato.getNombre() + "\":" + candidato.getNumVotos() + ",";
            else
                jsonStr += "\"" + candidato.getNombre() + "\":" + candidato.getNumVotos() + "}";
        }
        System.out.println(jsonStr);
        return jsonStr;
    }

    @Override
    public void run() {
        while (!startSendingInfo) {
            System.out.print("");
        }

        String str = createJsonStr();

        JSONParser parser = new JSONParser();

        Object obj = null;
        try {
            obj = parser.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jObj = (JSONObject) obj;
        this.setInfo(jObj);

        startSendingInfo = false;
    }

}
