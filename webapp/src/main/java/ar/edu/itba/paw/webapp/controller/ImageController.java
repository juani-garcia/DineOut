package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.persistence.Image;
import ar.edu.itba.paw.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] image(@PathVariable final long id) {
        Image image = imageService.getById(id).orElseThrow(() -> new RuntimeException("No such image for id: " + id));
        return image.getSource();
    }

}
