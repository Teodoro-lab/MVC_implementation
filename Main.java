import controllers.CandidatoController;
import models.Candidato;
import views.JavaSwingUI;

public class Main {
    public static void main(String[] args) {
        Candidato nullCandidato = Candidato.getNullInstance();
        CandidatoController ctr = new CandidatoController();
        JavaSwingUI appJavaSwingUI = new JavaSwingUI("App", ctr);

        ctr.mapModelWithView(nullCandidato, appJavaSwingUI);

    }
}
