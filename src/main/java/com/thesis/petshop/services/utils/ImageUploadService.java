package com.thesis.petshop.services.utils;

/*
 * Created: renzb
 * Date: 8/22/2022
 */

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class ImageUploadService {

    public String fileUpload(MultipartFile file, String imageName) {
        try {
            File path = new File("C:\\PET_SHOP\\images\\" + imageName + ".jpg");
            path.createNewFile();
            FileOutputStream output = new FileOutputStream(path);
            output.write(file.getBytes());
            output.close();
            return path.toString();
        } catch (Exception e) {
            //ignored
        }
        return "";
    }

}
