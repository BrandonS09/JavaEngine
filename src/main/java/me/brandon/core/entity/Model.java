package me.brandon.core.entity;

public class Model {
    private int id;
    private int vertexCount;
    private Texture texture;

    public Model(int id, int vertexCount){
        this.id = id;
        this.vertexCount = vertexCount;
    }

    public Model(Model model, Texture texture) {
        this.texture = texture;
        this.id = model.getId();
        this.vertexCount = model.getVertexCount();
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getId() {
        return id;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
