package dam.JosantVarona.test;

import dam.JosantVarona.Model.DAO.EjercicioDAO;
import dam.JosantVarona.Model.DAO.MultimediaDAO;
import dam.JosantVarona.Model.Entity.Exercise;
import dam.JosantVarona.Model.Entity.Multimedia;
import dam.JosantVarona.Model.Entity.Photos;
import dam.JosantVarona.View.MultimediaController;

import java.io.IOException;
import java.io.File;

public class inserMul {
    public static void main(String[] args) throws IOException {
        MultimediaDAO mu = new MultimediaDAO();
            File file = new File("C:\\Users\\josant\\IdeaProjects\\PeopleFit2\\src\\main\\resources\\dam\\JosantVarona\\View\\logo.jpg");
            byte[] imagen = MultimediaController.imagenToBytes(file);
        Exercise exercise = EjercicioDAO.build().findByid(3);
        Photos photos = new Photos("bizeps","png",exercise,imagen);
        mu.save(photos);

    }
}
