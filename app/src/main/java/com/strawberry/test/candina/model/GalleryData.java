package com.strawberry.test.candina.model;

import com.strawberry.test.candina.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryData {
    private static int[] pictureResource = {R.drawable.gallery_image_a, R.drawable.gallery_image_b, R.drawable.gallery_image_c,
            R.drawable.gallery_image_d, R.drawable.gallery_image_e, R.drawable.gallery_image_f, R.drawable.gallery_image_g,
            R.drawable.gallery_image_h, R.drawable.gallery_image_i, R.drawable.gallery_image_j};

    public static List<InternalData> getGalleryListData() {
        List <InternalData> data = new ArrayList<>();
        for (int count = 0; count  < pictureResource.length; count++) {
            InternalData item = new InternalData();
            item.setPictureResource(pictureResource[count]);
            data.add(item);
        }
        return data;
    }
}
