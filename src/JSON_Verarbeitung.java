
import Model.Feld;
import Model.LevelModel;
import Model.Gegenstand;
import org.JSONArray;
import org.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JSON_Verarbeitung {
    private LevelModel levelModel;

    public JSON_Verarbeitung(LevelModel levelModel) {
        this.levelModel = levelModel;

        //levelModel.addObserver(this);
    }

    //JSON-File wird eingelesen
    public void load_json_file(String path) throws IOException {
        String jsonString = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject object=new JSONObject(jsonString);
        read_name(object);
        read_width(object);
        read_height(object);
        read_gems(object);
        read_ticks(object);
        read_pre(object);
        read_post(object);
        read_maxslime(object);
        read_map(object);
        //System.out.println(object);
    }

    public void read_name(JSONObject jsonObject){
        String name = jsonObject.getString("name");
        levelModel.name = name;
    }

    public void read_width(JSONObject jsonObject){
        int width = jsonObject.getInt("width");
        levelModel.width = width;
    }

    public void read_height(JSONObject jsonObject){
        int height = jsonObject.getInt("height");
        levelModel.height = height;
    }

    public void read_gems(JSONObject jsonObject){
        JSONArray gems = jsonObject.optJSONArray("gems");
        for (int i=0; i<gems.length(); i++){
            int agem = gems.getInt(i);
            levelModel.gems[i] = agem;
        }
    }

    public void read_ticks(JSONObject jsonObject){
        JSONArray ticks = jsonObject.optJSONArray("ticks");
        for (int i=0; i<ticks.length(); i++){
            int atick = ticks.getInt(i);
            levelModel.ticks[i] = atick;
        }
    }

    public void read_pre(JSONObject jsonObject){    //vervollständigen, wenn pre-Regeln in json-file vorhanden
        if (jsonObject.has("pre")){
            JSONArray pre = jsonObject.optJSONArray("pre");
            List<String> apre = new ArrayList<>();
            for (int i=0; i<pre.length(); i++){
                apre.add(pre.getString(i));
            }
            levelModel.pre.add(apre);
        }
    }

    public void read_post(JSONObject jsonObject){    //vervollständigen, wenn post-Regeln in json-file vorhanden
        if (jsonObject.has("post")){
            JSONArray post = jsonObject.optJSONArray("post");
            List<String> apost = new ArrayList<>();
            for (int i=0; i<post.length(); i++){
                apost.add(post.getString(i));
            }
            levelModel.post.add(apost);
        }
    }

    public void read_maxslime(JSONObject jsonObject){
        if (jsonObject.has("maxslime")){
            int maxslime = jsonObject.getInt("maxslime");
            levelModel.maxslime = maxslime;
        }
    }

    public void read_map(JSONObject jsonObject){
        JSONArray map = jsonObject.optJSONArray("map");
        for (int i=0; i<map.length(); i++){
            JSONArray zeile = map.getJSONArray(i);
            for (int j=0; j<zeile.length(); j++){
                Feld feld = new Feld(null,0,0,0,0,0,0,0,0,0,0,0,0);
                String token = "";
                if (zeile.get(j) instanceof JSONObject) {
                    JSONObject afeld = zeile.getJSONObject(j);
                    token = afeld.getString("token");
                    JSONObject values = afeld.getJSONObject("values");      //andere values müssen noch in betracht gezogen werden
                    if (values.has("moved")) {
                        int moved = values.getInt("moved");
                        feld.setMoved(moved);
                    }

                    if (values.has("falling")) {
                        int falling = values.getInt("falling");
                        feld.setFalling(falling);
                    }

                    if (values.has("loose")) {
                        int loose = values.getInt("loose");
                        feld.setLoose(loose);
                    }

                    if (values.has("slippery")) {
                        int slippery = values.getInt("slippery");
                        feld.setSlippery(slippery);
                    }

                    if (values.has("pushable")) {
                        int pushable = values.getInt("pushable");
                        feld.setSlippery(pushable);
                    }
                    if (values.has("bam")) {
                        int bam = values.getInt("bam");
                        feld.setBam(bam);
                    }

                    if (values.has("bamrich")) {
                        int bamrich = values.getInt("bamrich");
                        feld.setBamrich(bamrich);
                    }

                    if (values.has("direction")) {
                        int direction = values.getInt("direction");
                        feld.setDirection(direction);
                    }

                    if (values.has("a")) {
                        int a = values.getInt("a");
                        feld.setA(a);
                    }

                    if (values.has("b")) {
                        int b = values.getInt("b");
                        feld.setB(b);
                    }

                    if (values.has("c")) {
                        int c = values.getInt("c");
                        feld.setC(c);
                    }

                    if (values.has("d")) {
                        int d = values.getInt("d");
                        feld.setD(d);
                    }
                }

                if (zeile.get(j).equals("foo")) {
                    //JSONObject afeld = zeile.getJSONObject(j);
                    feld.setToken(null);
                    feld.setDirection(0);
                }
                else if (zeile.get(j).equals("me") || token.equals("me")) {
                    feld.setToken(Gegenstand.ME);
                }
                else if (zeile.get(j).equals("mud") || token.equals("mud")) {
                    feld.setToken(Gegenstand.MUD);
                }
                else if (zeile.get(j).equals("stone") || token.equals("stone")) {
                    feld.setToken(Gegenstand.STONE);
                }

                else if (zeile.get(j).equals("gem") || token.equals("gem")) {
                    feld.setToken(Gegenstand.GEM);
                }

                else if (zeile.get(j).equals("exit") || token.equals("exit")) {
                    feld.setToken(Gegenstand.EXIT);
                }

                else if (zeile.get(j).equals("wall") || token.equals("wall")) {
                    feld.setToken(Gegenstand.WALL);
                }

                else if (zeile.get(j).equals("bricks") || token.equals("bricks")) {
                    feld.setToken(Gegenstand.BRICKS);
                }

                else if (zeile.get(j).equals("path") || token.equals("path")) {
                    feld.setToken(Gegenstand.PATH);
                }

                else if (zeile.get(j).equals("explosion") || token.equals("explosion")) {
                    feld.setToken(Gegenstand.EXPLOSION);
                }

                else if (zeile.get(j).equals("slime") || token.equals("slime")) {
                    feld.setToken(Gegenstand.SLIME);
                }

                else if (zeile.get(j).equals("swapling") || token.equals("swapling")) {
                    feld.setToken(Gegenstand.SWAPLING);
                }

                else if (zeile.get(j).equals("blockling") || token.equals("blockling")) {
                    feld.setToken(Gegenstand.BLOCKLING);
                }

                else if (zeile.get(j).equals("xling") || token.equals("xling")) {
                    feld.setToken(Gegenstand.XLING);
                }

                else if (zeile.equals("ghostling") || token.equals("ghostling")) {
                    feld.setToken(Gegenstand.GHOSTLING);
                }

                else if (zeile.equals("fire") || token.equals("fire")) {
                    feld.setToken(Gegenstand.FIRE);
                }

                else if (zeile.equals("norththing") || token.equals("norththing")) {
                    feld.setToken(Gegenstand.NORTHTHING);
                }

                else if (zeile.equals("eastthing") || token.equals("eastthing")) {
                    feld.setToken(Gegenstand.EASTTHING);
                }

                else if (zeile.equals("souththing") || token.equals("souththing")) {
                    feld.setToken(Gegenstand.SOUTHTHING);
                }

                else if (zeile.equals("westthing") || token.equals("westthing")) {
                    feld.setToken(Gegenstand.WESTTHING);
                }

                else if (zeile.equals("pot") || token.equals("pot")) {
                    feld.setToken(Gegenstand.POT);
                }

                else if (zeile.equals("sieve") || token.equals("sieve")) {
                    feld.setToken(Gegenstand.SIEVE);
                }

                else if (zeile.equals("sand") || token.equals("sand")) {
                    feld.setToken(Gegenstand.SAND);
                }

                levelModel.setMap(new Feld[j][i]);
            }
        }
    }
}
