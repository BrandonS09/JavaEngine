package me.brandon.test;

import me.brandon.core.ILogic;
import me.brandon.core.ObjectLoader;
import me.brandon.core.entity.Entity;
import me.brandon.core.entity.Model;
import me.brandon.core.RenderManager;
import me.brandon.core.WindowManager;
import me.brandon.core.entity.Texture;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestGame implements ILogic {
    private int direction = 0;
    private float colour = 0.0f;

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;
    private Entity entity;

    public TestGame() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
    }
    @Override
    public void init() throws Exception {
        renderer.init();

        float[] vertices = {
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };
        Model model = loader.loadModel(vertices, textureCoords, indices);
        model.setTexture(new Texture(loader.loadTexture("C:\\Users\\Brandon Shen\\Documents\\Engine\\JavaEngine\\textures\\grassblock.png")));
        entity = new Entity(model, new Vector3f(1,0,0), new Vector3f(0,0,0), 1);
    }

    @Override
    public void input() {
        if(window.isKeyPressed(GLFW.GLFW_KEY_UP)){
            direction = 1;
        }
        else if (window.isKeyPressed(GLFW.GLFW_KEY_DOWN))
            direction = -1;
        else direction = 0;
    }

    @Override
    public void update() {
        colour += direction * .01f;
        if (colour > 1)
            colour = 1.0f;
        else if (colour <= 0) colour = 0.0f;

        if (entity.getPos().x < -1.5f)
            entity.getPos().x = 1.5f;
        entity.getPos().x -= 0.01f;
    }

    @Override
    public void render() {
        if (window.isResize()){
            GL11.glViewport(0,0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColour(colour, colour, colour, 0.0f);
        renderer.render(entity);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
        window.cleanup();
        GLFW.glfwTerminate();
    }
}
