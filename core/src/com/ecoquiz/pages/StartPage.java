package com.ecoquiz.pages;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ecoquiz.Main;
import com.ecoquiz.ldutils.ui.LdButton;
import com.ecoquiz.ldutils.ui.LdText;

import static com.ecoquiz.Main.HALF_HEIGHT;
import static com.ecoquiz.Main.HALF_WIDHT;
import static com.ecoquiz.Main.HEIGHT;
import static com.ecoquiz.Main.WIDTH;
import static com.ecoquiz.Main.clDark;
import static com.ecoquiz.Main.getTexture;


/**
 * Created by Helga on
 */
public class StartPage {
    private Main main;
    private TextureRegion imgStart;
    private LdButton butStart;


    public StartPage(Main m) {
        this.main = m;
        imgStart = getTexture("img_start");
        butStart = new LdButton(getTexture("but"), HALF_WIDHT - 190, HALF_HEIGHT - 815, 380, 100, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.playPage.show(true);


            }
        });
        butStart.addText("Начать", 48, 0, 67, 380, Color.WHITE, Align.center, false);
        butStart.setLimPres(0.25f);
    }


    public void show() {
        main.page = Main.Pages.Start;
        main.stage.clear();
        main.stage.addActor(butStart);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(imgStart, 0, HEIGHT - 1530, WIDTH, 1530);
        LdText.draw("Эко \nВикторина",110, 0, HEIGHT-400, WIDTH, Color.WHITE, Align.center, true);
        butStart.draw(batch);
    }


}
