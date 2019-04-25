import javax.annotation.processing.Messager;

@Anno(className = "Employe", attributesType = {"int", "String"}, attributesName = {"nombre_value", "nom"})
public class AnnosWithoutProcessors {

    Employe employe1 = new Employe();

    public void main() {
        System.out.println("J'ai bien cree employe1");
    }
}
