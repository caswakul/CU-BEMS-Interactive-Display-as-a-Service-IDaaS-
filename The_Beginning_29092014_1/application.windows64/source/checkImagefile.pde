void preecha_checkImgfiles(String preecha_pictureFolder) {
  String[] outList_of_foundImageFiles = {
  };
  String[] list_of_imageFileSuffixes = {
    "jpg", "png"
  };
  File file = new File(preecha_pictureFolder);
  String[] names = file.list();

  for (int numberImage =0; numberImage < names.length; numberImage++ ) {
    String[] curr_filenameSplit = splitTokens( names[numberImage], "." );

    for ( int fileSuffix_i = 0; fileSuffix_i < list_of_imageFileSuffixes.length; fileSuffix_i++ ) { 
      String examinedFile_filesuffix = curr_filenameSplit[1] ;
      String listOfValid_fileSuffixed = list_of_imageFileSuffixes[fileSuffix_i] ;

      if ( examinedFile_filesuffix.equals( listOfValid_fileSuffixed ) ) {

        outList_of_foundImageFiles = append( outList_of_foundImageFiles, preecha_pictureFolder+names[numberImage] );
      }
    }
  }



  for (int i =0; i < outList_of_foundImageFiles.length; i++) {
    if (preecha_pictureFolder == preecha_pictureFolder_canvas1) {
      preecha_allImages.add(loadImage(outList_of_foundImageFiles[i]));
      (preecha_allImages.get(i)).resize(width, height - menuHeight_margin);
    } 
    if (preecha_pictureFolder == preecha_pictureFolder_canvas3) {
      preecha_allImages_canvas3.add(loadImage(outList_of_foundImageFiles[i]));
      (preecha_allImages_canvas3.get(i)).resize(width, height - menuHeight_margin);
    } 
    if (preecha_pictureFolder == preecha_pictureFolder_canvas0) {
      preecha_allImages_canvas0.add(loadImage(outList_of_foundImageFiles[i]));
      (preecha_allImages_canvas0.get(i)).resize(640, 400);
    }
  }
}

