package com.company.data;

import com.company.logic.World;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Data {
    public static void save(World obj) {
        try {
            FileOutputStream f = new FileOutputStream("save.txt");
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(obj);

            o.close();
            f.close();

        } catch (Exception ignored) {}
    }

    public static World load() {
        try {
            FileInputStream fi = new FileInputStream("save.txt");
            ObjectInputStream oi = new ObjectInputStream(fi);

            World w = (World) oi.readObject();

            oi.close();
            fi.close();

            return w;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new World(false);
    }
}
