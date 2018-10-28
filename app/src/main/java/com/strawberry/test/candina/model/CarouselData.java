package com.strawberry.test.candina.model;

import com.strawberry.test.candina.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarouselData {
    private static int[] pictureResource = {R.drawable.gallery_image_a, R.drawable.gallery_image_b, R.drawable.gallery_image_c,
            R.drawable.gallery_image_d, R.drawable.gallery_image_e, R.drawable.gallery_image_f, R.drawable.gallery_image_g,
            R.drawable.gallery_image_h, R.drawable.gallery_image_i, R.drawable.gallery_image_j};

    public static List<InternalData> getCarouselListData() {
        int[] randomPictureResource = pickRandomPicture(pictureResource, 5);
        List <InternalData> data = new ArrayList<>();
        for (int count = 0; count  < randomPictureResource.length; count++) {
            InternalData item = new InternalData();
            item.setRandomPictureResource(randomPictureResource[count]);
            data.add(item);
        }
        return data;
    }

    public static int[] pickRandomPicture(int[] pictureArray, int n) {
        List<Integer> list = new ArrayList<>(pictureArray.length);
        for (int i : pictureArray) {
            list.add(i);
        }
        Collections.shuffle(list);
        int[] randomPictureArray = new int[n];
        for (int i = 0; i < n; i++) {
            randomPictureArray[i] = list.get(i);
        }
        return randomPictureArray;
    }
}
