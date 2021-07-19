package com.ecoquiz.pages;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ecoquiz.Main;
import com.ecoquiz.ldutils.ui.LdButton;
import com.ecoquiz.ldutils.ui.LdLinkButton;
import com.ecoquiz.ldutils.ui.LdText;

import static com.ecoquiz.Main.HALF_HEIGHT;
import static com.ecoquiz.Main.HALF_WIDHT;
import static com.ecoquiz.Main.HEIGHT;
import static com.ecoquiz.Main.WIDTH;
import static com.ecoquiz.Main.clDark;
import static com.ecoquiz.Main.clLight;
import static com.ecoquiz.Main.clRed;
import static com.ecoquiz.Main.clText;
import static com.ecoquiz.Main.getTexture;


/**
 * Created by Helga on
 */
public class ResultPage {
    private Main main;
    private TextureRegion imgStart, fieldResult, iconResTrue, iconResFalse;
    private LdLinkButton showRes;
    private LdButton butAgain;


    public ResultPage(Main m) {
        this.main = m;

        iconResTrue = getTexture("icon_true");
        iconResFalse = getTexture("icon_false");
        fieldResult = getTexture("field_result");
        butAgain = new LdButton(getTexture("but"), HALF_WIDHT - 190, HALF_HEIGHT - 815, 380, 100, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.playPage.show(true);
            }
        });

        butAgain.addText("Заново", 48, 0, 67, 380, Color.WHITE, Align.center, false);
        butAgain.setLimPres(0.25f);
        showRes = new LdLinkButton("Посмотреть ответы", 48, clDark, clText, HALF_WIDHT, butAgain.getY() - 135, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.playPage.show(false);
            }
        });

    }


    public void show() {
        main.page = Main.Pages.Result;
        main.stage.clear();
        main.stage.addActor(butAgain);
        main.stage.addActor(showRes);
    }

    public void draw(SpriteBatch batch) {
        butAgain.draw(batch);
        batch.draw(fieldResult, HALF_WIDHT - 375, butAgain.getY() + 705);
        LdText.draw(main.score + "", 144, HALF_WIDHT - 375, butAgain.getY() + 1135, 750, clDark, Align.center, false);
        LdText.draw("из " + main.questions.size, 48, HALF_WIDHT - 375, butAgain.getY() + 975, 750, clDark, Align.center, false);
        LdText.draw("Ваш результат", 110, 0, HEIGHT - 115, WIDTH, clDark, Align.center, false);
        main.field.draw(batch, HALF_WIDHT - 470, butAgain.getY() + 505, 940, 110);
        main.field.draw(batch, HALF_WIDHT - 470, butAgain.getY() + 345, 940, 110);
        LdText.draw("Правильных ответов: ", 48, HALF_WIDHT - 435, butAgain.getY() + 582, 700, clDark, Align.left, false);
        LdText.draw("Неправильных ответов: ", 48, HALF_WIDHT - 435, butAgain.getY() + 417, 700, clRed, Align.left, false);
        LdText.draw(main.score + "", 48, HALF_WIDHT + 295, butAgain.getY() + 582, 700, clDark, Align.left, false);
        LdText.draw((main.questions.size - main.score) + "", 48, HALF_WIDHT + 295, butAgain.getY() + 417, 700, clRed, Align.left, false);
        batch.draw(iconResFalse, HALF_WIDHT + 375, butAgain.getY() + 370, 60, 60);
        batch.draw(iconResTrue, HALF_WIDHT + 375, butAgain.getY() + 535, 60, 60);
        showRes.draw(batch);
    }


}
