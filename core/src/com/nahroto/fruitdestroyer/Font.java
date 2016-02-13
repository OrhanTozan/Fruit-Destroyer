package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public class Font
{
    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();

    private float width;
    private float height;

    private int size;
    private Color color;

    public Font(final Application APP, String fileName, String filePath, int size, Color color, boolean filter)
    {
        // ASSIGN ATTRIBUTES
        this.size = size;
        this.color = color;

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        FileHandleResolver resolver = new InternalFileHandleResolver();

        APP.assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        APP.assets.setLoader(BitmapFont.class, ".otf", new FreetypeFontLoader(resolver));

        // GENERATE FONT
        if (filter) // IF FILTER ENABLED
        {
            parameter.fontParameters.genMipMaps = true;
            parameter.fontParameters.minFilter = Texture.TextureFilter.Linear;
            parameter.fontParameters.magFilter = Texture.TextureFilter.Linear;
        }
        parameter.fontFileName = filePath;
        parameter.fontParameters.size = size;
        parameter.fontParameters.color = color;

        APP.assets.load(fileName, BitmapFont.class, parameter);
        APP.assets.finishLoading();

        font = APP.assets.get(fileName, BitmapFont.class);
    }

    public Font(final Application APP, String fileName, String filePath, int size, boolean filter)
    {
        // ASSIGN ATTRIBUTES
        this.size = size;

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        FileHandleResolver resolver = new InternalFileHandleResolver();

        APP.assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        APP.assets.setLoader(BitmapFont.class, ".otf", new FreetypeFontLoader(resolver));

        // GENERATE FONT
        if (filter) // IF FILTER ENABLED
        {
            parameter.fontParameters.genMipMaps = true;
            parameter.fontParameters.minFilter = Texture.TextureFilter.Linear;
            parameter.fontParameters.magFilter = Texture.TextureFilter.Linear;
        }
        parameter.fontFileName = filePath;
        parameter.fontParameters.size = size;

        APP.assets.load(fileName, BitmapFont.class, parameter);
        APP.assets.finishLoading();

        font = APP.assets.get(fileName, BitmapFont.class);
    }

    public Font(final Application APP, String fileName, String filePath, int size, Color color, Color borderColor, float borderWidth, boolean filter)
    {
        // ASSIGN ATTRIBUTES
        this.size = size;
        this.color = color;

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        FileHandleResolver resolver = new InternalFileHandleResolver();

        APP.assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        APP.assets.setLoader(BitmapFont.class, ".otf", new FreetypeFontLoader(resolver));

        // GENERATE FONT
        if (filter) // IF FILTER ENABLED
        {
            parameter.fontParameters.genMipMaps = true;
            parameter.fontParameters.minFilter = Texture.TextureFilter.Linear;
            parameter.fontParameters.magFilter = Texture.TextureFilter.Linear;
        }
        parameter.fontFileName = filePath;
        parameter.fontParameters.size = size;
        parameter.fontParameters.color = color;
        parameter.fontParameters.borderColor = borderColor;
        parameter.fontParameters.borderWidth = borderWidth;

        APP.assets.load(fileName, BitmapFont.class, parameter);
        APP.assets.finishLoading();

        font = APP.assets.get(fileName, BitmapFont.class);
    }

    public void render(SpriteBatch batch, String text, float x, float y, boolean center)
    {
        if (center)
        {
            x -= getWidth(text) / 2;
            y += getHeight(text) / 2;
        }

        font.draw(batch, text, x, y);
    }

    public float getWidth(String text)
    {
        layout.setText(font, text);
        width = layout.width;
        return width;
    }

    public float getHeight(String text)
    {
        layout.setText(font, text);
        height = layout.height;
        return height;
    }

    public int getSize()
    {
        return size;
    }

    public Color getColor()
    {
        return color;
    }

    public BitmapFont getFont()
    {
        return font;
    }

    public void dispose()
    {
        font.dispose();
    }
}