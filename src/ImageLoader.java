
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class ImageLoader {
    public void load_image(ImageView imageView, String file_name) throws FileNotFoundException {
        try {

            File file = new File(System.getProperty("user.dir") + "/images/" + file_name);
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            imageView.setImage(image);

            double maxWidth = 400;
            double maxHeight = 300;

            // Για να προσαρμοστεί η εικόνα στο παράθυρο
            double width = image.getWidth();
            double height = image.getHeight();
            double scale = Math.min(maxWidth / width, maxHeight / height);
            imageView.setFitWidth(scale * width);
            imageView.setFitHeight(scale * height);

            imageView.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}