public class ClasseDeTest {

    public int nombre;

    @Anno(className = "Directeur")
    public int uneMethode() {
        return this.nombre;
    }
}
