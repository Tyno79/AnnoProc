import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;

//Pour faire fonctionner ce processeur d'annotation, utilsez la commande javac AnnoProc.java
//Puis pour lancer la compilation sur les fichiers nécessaires utilisez javac -cp . -processor AnnoProc -nom du/des fichier(s) à analyser-

@SupportedAnnotationTypes("Anno")
public class AnnoProc extends AbstractProcessor {
    public boolean process(Set<? extends TypeElement> elems, RoundEnvironment renv) {
        Messager messager = processingEnv.getMessager();
        Elements eltUtils = processingEnv.getElementUtils();
        Filer filer = processingEnv.getFiler();

        TypeElement elementAnno = eltUtils.getTypeElement("Anno");

        Set<? extends Element> elements = renv.getElementsAnnotatedWith(elementAnno);

        if(!elements.isEmpty()) {

            try {
                for (Element el:elements) {
                    Anno anno = (Anno) el.getAnnotation(Anno.class);
                    messager.printMessage(Diagnostic.Kind.NOTE, "Je vais try");
                    PrintWriter pw = new PrintWriter(filer.createResource( StandardLocation.SOURCE_OUTPUT , "", anno.className() + ".java").openOutputStream());
                    pw.println("public class " + (anno).className() + " {");

                    for(int i = 0; i < (anno).attributesType().length; i++) {
                        pw.println("\t" + (anno).attributesType()[i] + " " + (anno).attributesName()[i] + ";");
                    }
                    pw.println("");

                    for(int i = 0; i < (anno).attributesType().length; i++) {
                        String attributesCamelCase = (anno).attributesName()[i].substring(0, 1).toUpperCase() + (anno).attributesName()[i].substring(1);
                        pw.println("\tpublic " + (anno).attributesType()[i] + " get" + attributesCamelCase +
                                    "() {\n\t\treturn this." + (anno).attributesName()[i] + ";\n\t}"
                        );
                        pw.println("\tpublic void set" + attributesCamelCase + "(" + (anno).attributesType()[i]
                                    + " arg) {\n\t\t" + "this." + (anno).attributesName()[i] + " = arg;\n\t}"
                        );
                    }

                    pw.println("}");
                    pw.close();
                    messager.printMessage(Diagnostic.Kind.NOTE, "J'ai try");
                }
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.NOTE, "J'ai catch une erreur");
                e.printStackTrace();
            }
        }
        return true;
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}