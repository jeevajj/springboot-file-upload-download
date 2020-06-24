package com.io.content;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ContentManagerController {

    private static String UPLOADED_FOLDER = "/home/jeeva/TEMP/";

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
           System.out.print("Empty file");
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "file has been uploaded successfully";
    }

   @GetMapping("/download")
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
       InputStream is = new FileInputStream(new File (UPLOADED_FOLDER+fileName));
       org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
       String filename = "attachment;" + "filename = "+fileName;
       response.setContentType("application/octet-stream");
       response.setHeader("Content - Disposition", "attachment; filename = "+filename + ".pdf");
       response.flushBuffer();
   }

}
