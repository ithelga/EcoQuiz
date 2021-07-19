package com.ecoquiz.pages;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ecoquiz.Main;
import com.ecoquiz.Question;
import com.ecoquiz.ldutils.ui.LdButton;
import com.ecoquiz.ldutils.ui.LdLinkButton;
import com.ecoquiz.ldutils.ui.LdText;

import static com.ecoquiz.Main.HALF_HEIGHT;
import static com.ecoquiz.Main.HALF_WIDHT;
import static com.ecoquiz.Main.HEIGHT;
import static com.ecoquiz.Main.clDark;
import static com.ecoquiz.Main.clLight;
import static com.ecoquiz.Main.clRed;
import static com.ecoquiz.Main.clText;
import static com.ecoquiz.Main.getTexture;


/**
 * Created by Helga on
 */
public class PlayPage {
    private Main main;
    private TextureRegion imgStart;
    private LdButton butNext;
    private LdButton[] buttons;
    private int num, qSize;
    private String text;
    private int[][] userAnswers;
    private boolean isNewPlay, isCorrect;
    private LdLinkButton startRes;


    public PlayPage(Main m) {
        this.main = m;
        butNext = new LdButton(getTexture("but"), HALF_WIDHT - 190, HALF_HEIGHT - 815, 380, 100, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isNewPlay) nextQuestion();
                else showAnswer();

            }
        });

        butNext.addText("Далeе", 48, 0, 67, 380, Color.WHITE, Align.center, false);
        butNext.setLimPres(0.25f);

        startRes = new LdLinkButton("Результаты", 48, clDark, clText, HALF_WIDHT, butNext.getY() - 135, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.resultPage.show();
            }
        });

    }


    public void show(boolean isNewPlay) {
        this.isNewPlay = isNewPlay;
        main.page = Main.Pages.Play;
        main.stage.clear();
        main.stage.addActor(butNext);
        main.stage.addActor(startRes);
        num = -1;
        if (isNewPlay) {
            startRes.setVisible(false);
            main.questions.shuffle();
            main.score = 0;
            userAnswers = new int[main.questions.size][];
            nextQuestion();
        } else {
            showAnswer();
            startRes.setVisible(true);
        }

    }

    public void draw(SpriteBatch batch) {
        butNext.draw(batch);
        LdText.draw("Вопрос " + (num + 1) + "/" + main.questions.size, 48, 70, HEIGHT - 100, 940, clDark, LdText.F2, Align.right, false);
        LdText.draw(text, qSize, HALF_WIDHT - 470, HEIGHT - 200, 940, clText, Align.center, true);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].draw(batch);
        }
        if (!isNewPlay) {
            LdText.draw(isCorrect ? "Верный ответ" : "Неверный ответ", 48, 70, HEIGHT - 100, 940, isCorrect ? clText : clRed, LdText.F2, Align.left, false);
            startRes.draw(batch);
        }
    }

    private void nextQuestion() {
        if (num != -1) {
            boolean isCorrect = true;
            boolean isAllEmpty = true;
            userAnswers[num] = new int[buttons.length];
            for (int i = 0; i < buttons.length; i++) {
                userAnswers[num][i] = buttons[i].getState() - 1;
                if (userAnswers[num][i] != main.questions.get(num).getCorrects()[i])
                    isCorrect = false;
                if (buttons[i].getState() == LdButton.STATE_2) isAllEmpty = false;
            }
            if (isAllEmpty) return;
            if (isCorrect) main.score++;
        }

        num++;
        if (num >= main.questions.size) {
            main.resultPage.show();
            return;
        }
        if (buttons != null) {
            for (LdButton but : buttons) {
                but.remove();
            }
        }

        Question q = main.questions.get(num);
        q.shuffleAnswers();
        qSize = 48;
        text = q.getQuestion();
        float qH = LdText.getHeight(text, qSize, 940, true);

        if (qH > 430) {
            qSize = 36;
            qH = LdText.getHeight(text, qSize, 940, true);
        }

        float bY = HEIGHT - 200 - qH - 50;
        buttons = new LdButton[q.getAnswers().length];
        for (int i = 0; i < buttons.length; i++) {
            int size = 48;
            float bH = LdText.getHeight(q.getAnswers()[i], size, 775, true) + 70;
            if (bH > 300) {
                size = 36;
                bH = LdText.getHeight(q.getAnswers()[i], size, 775, true) + 70;
            }
            bY -= bH + 40;
            final int j = i;
            buttons[i] = new LdButton(getTexture("field"), HALF_WIDHT - 470, bY, 940, bH, new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buttons[j].setState(buttons[j].getState() == LdButton.STATE_1 ? LdButton.STATE_2 : LdButton.STATE_1);
                    buttons[j].setColor(buttons[j].getState() == LdButton.STATE_1 ? Color.WHITE : clLight);
                }
            });

            buttons[i].setNinePath(main.field);
            buttons[i].setLimPres(0.1f);
            buttons[i].setState(LdButton.STATE_1);
            buttons[i].addText(q.getAnswers()[i], size, 35, buttons[i].getHeight() - 35, 775, clDark, Align.left, true);
            buttons[i].addSprite(getTexture("but_check1"), 845, (int) (buttons[i].getHeight() - 85), 60, 60, LdButton.STATE_1);
            buttons[i].addSprite(getTexture("but_check2"), 845, (int) (buttons[i].getHeight() - 85), 60, 60, LdButton.STATE_2);
            main.stage.addActor(buttons[i]);
        }
    }

    private void showAnswer() {
        num++;
        if (num >= main.questions.size) {
            main.resultPage.show();
            return;
        }

        Question q = main.questions.get(num);
        qSize = 48;
        text = q.getQuestion();
        float qH = LdText.getHeight(text, qSize, 940, true);

        if (qH > 430) {
            qSize = 36;
            qH = LdText.getHeight(text, qSize, 940, true);
        }


        float bY = HEIGHT - 200 - qH - 50;
        isCorrect = true;
        buttons = new LdButton[q.getAnswers().length];
        for (int i = 0; i < buttons.length; i++) {
            int size = 48;
            float bH = LdText.getHeight(q.getAnswers()[i], size, 775, true) + 70;
            if (bH > 300) {
                size = 36;
                bH = LdText.getHeight(q.getAnswers()[i], size, 775, true) + 70;
            }
            bY -= bH + 40;

            if (userAnswers[num][i] != main.questions.get(num).getCorrects()[i])
                isCorrect = false;

            buttons[i] = new LdButton(getTexture("field"), HALF_WIDHT - 470, bY, 940, bH);
            buttons[i].setNinePath(main.field);
            buttons[i].setColor(userAnswers[num][i] == 1 ? clLight : Color.WHITE);
            buttons[i].addText(q.getAnswers()[i], size, 35, buttons[i].getHeight() - 35, 775, clDark, Align.left, true);
            buttons[i].addSprite(getTexture(main.questions.get(num).getCorrects()[i] == 1 ? "icon_true" : "icon_false"), 845, (int) (buttons[i].getHeight() - 85), 60, 60);
        }


    }
}
