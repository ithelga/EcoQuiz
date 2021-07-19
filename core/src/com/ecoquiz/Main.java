package com.ecoquiz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.ecoquiz.ldutils.Graphics;
import com.ecoquiz.ldutils.LdDelta;
import com.ecoquiz.ldutils.LdMask;
import com.ecoquiz.ldutils.ui.LdText;
import com.ecoquiz.pages.PlayPage;
import com.ecoquiz.pages.ResultPage;
import com.ecoquiz.pages.StartPage;

import java.util.Collections;

public class Main extends ApplicationAdapter {

    public static int WIDTH, HEIGHT, HALF_WIDHT, HALF_HEIGHT;
    public static float AR;
    private SpriteBatch batch;
    public Pages page;

    private static TextureAtlas atlas;
    private OrthographicCamera camera;
    public static Color clBackground = Color.valueOf("E9EBEA");
    public static Color clDark = Color.valueOf("36B5EC");
    public static Color clLight = Color.valueOf("CBEFFF");
    public static Color clRed = Color.valueOf("FF6565");
    public static Color clText = Color.valueOf("008BC7");
    public Stage stage;
    public Texture whiteTex;
    public NinePatch field;

    public enum Pages {Start, Play, Result}

    public StartPage startPage;
    public PlayPage playPage;
    public ResultPage resultPage;
    public Array<Question> questions;

    public int score;

    @Override
    public void create() {


        HEIGHT = 1920;
        WIDTH = HEIGHT * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        if (WIDTH < 1080) {
            WIDTH = 1080;
            HEIGHT = WIDTH * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
        }
        HALF_WIDHT = WIDTH / 2;
        HALF_HEIGHT = HEIGHT / 2;
        AR = (float) WIDTH / Gdx.graphics.getWidth();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("atlases/atlas.atlas");

        whiteTex = Graphics.getColorTexture(Color.WHITE);
        field = new NinePatch(getTexture("field"), 40, 40, 40, 40);

        LdMask.init(camera, Graphics.getEmptyTexture());
        LdText.init(batch, 2);
        LdText.add("fonts/reg48.fnt", 48, 57);
        LdText.add("fonts/reg110.fnt", 110, 130);
        LdText.add("fonts/med48.fnt", 48, 57, LdText.F2);
        LdText.add("fonts/med110.fnt", 110, 130, LdText.F2);

        startPage = new StartPage(this);
        playPage = new PlayPage(this);
        resultPage = new ResultPage(this);
        startPage.show();

        questionCreate();


    }

    @Override
    public void render() {
        float delta = LdDelta.getDelta();
//        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) backPressed();

        Gdx.gl.glClearColor(clBackground.r, clBackground.g, clBackground.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if (page == Pages.Start) startPage.draw(batch);
        if (page == Pages.Play) playPage.draw(batch);
        if (page == Pages.Result) resultPage.draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }

    public static TextureRegion getTexture(String name) {
        return atlas.findRegion(name);

    }

    private void questionCreate() {
        questions = new Array<>();
        questions.add(new Question("Комфортная и безопасная среда для жизни – это…", new String[]{
                "Национальный проект",
                "Государственная Программа",
                "Национальная цель развития Российской Федерации",
                "Целевой показатель национального проекта «Экология»"}, new int[]{0, 0, 1, 0}));
        questions.add(new Question("Что из нижеперечисленного не входит в перечень Государственных программ Российской Федерации в области экологии:",
                new String[]{
                        "Комплексное развитие сельских территорий",
                        "Развитие лесного хозяйства",
                        "Развитие рыбохозяйственного комплекса",
                        "Доступная среда"},
                new int[]{0, 0, 0, 1}));
        questions.add(new Question("Примерный объём финансового обеспечения реализации национального проекта «Экология»:",
                new String[]{"3,5 млрд рублей", "3,8 трлн рублей", "1,7 трлн рублей", "700 млн рублей"},
                new int[]{0, 1, 0, 0}));
        questions.add(new Question("Структурным элементом какой Государственной программы Российской Федерации является подпрограмма «Биологическое разнообразие России»?",
                new String[]{"Охрана окружающей среды", "Развитие рыбохозяйственного комплекса", "Воспроизводство и использование природных ресурсов", "Развитие лесного хозяйства"},
                new int[]{1, 0, 0, 0}));

        questions.add(new Question("Что из перечисленного обеспечивает достижение национальной цели «Комфортная и безопасная среда для жизни» в Российской Федерации?",
                new String[]{"Национальный проект «Экология»", "Государственные программы Российской Федерации в области экологии", "Федеральные проекты в области экологии", "Региональные проекты в области экологии"},
                new int[]{1, 1, 1, 1}));

        questions.add(new Question("Количество Федеральных проектов, входящих в структуру национального проекта «Экология»:",
                new String[]{"3", "5", "11", "Нет правильного ответа"},
                new int[]{0, 0, 1, 0}));


        questions.add(new Question("Государственная программа Российской Федерации, имеющая наибольший объём финансового обеспечения в 2020-2023 годы из числа реализуемых в сфере экологии:",
                new String[]{"Комплексное развитие сельских территорий",
                        "Охрана окружающей среды",
                        "Воспроизводство и использование природных ресурсов",
                        "Развитие лесного хозяйства"},
                new int[]{1, 0, 0, 0}));


        questions.add(new Question("На достижение какого целевого показателя направлено реализация следующего мероприятия: «Совершенствование системы раздельного сбора мусора и эффективного использования вторсырья»",
                new String[]{"Ликвидация наиболее опасных объектов накопленного вреда окружающей среде и экологическое оздоровление водных объектов, включая реку Волгу, озера Байкал и Телецкое",
                        "Снижение выбросов опасных загрязняющих веществ, оказывающих наибольшее негативное воздействие на окружающую среду и здоровье человека, в два раза",
                        "Создание устойчивой системы обращения с твердыми коммунальными отходами, обеспечивающей сортировку отходов в объеме 100 процентов и снижение объема отходов, направляемых на полигоны, в два раза"},
                new int[]{0, 0, 1}));


        questions.add(new Question("Информационные ресурсы по национальным проектам Российской Федерации:",
                new String[]{"Сайт «национальныепроекты.рф»", "Вкладка «Национальные проекты» на странице Электронного бюджета",
                        "Страницы «Национальные проекты России» в социальных сетях (Вконтакте, Instagram и др.)"},
                new int[]{1, 1, 1}));


        questions.add(new Question("По каким направлениям ведётся работа в рамках достижения национальных целей в области экологии?",
                new String[]{"Утилизация и переработка отходов", "Сохранение водоемов и повышение качества питьевой воды", "Уменьшение загрязнения воздуха",
                        "Защита природы и животных", "Внедрение наилучших природоохранных технологий"},
                new int[]{1, 1, 1, 1, 1}));

        questions.add(new Question("Возможности населения и акции, реализуемые в рамках реализации национального проекта «Экология»:",
                new String[]{"Посадить дерево", "Сортировать бытовые отходы", "Пройти заповедным маршрутом", "Убрать мусор на берегу"},
                new int[]{1, 1, 1, 1}));

        questions.add(new Question("К какому целевому показателю из Указа Президента Российской Федерации от 21 июля 2020 г. № 474 «О национальных целях развития Российской Федерации на период до 2030 года» относится фактор достижения национальной цели развития «Снижение антропогенной нагрузки на окружающую среду»?",
                new String[]{"Создание устойчивой системы обращения с твердыми коммунальными отходами, обеспечивающей сортировку отходов в объеме 100 процентов и снижение объема отходов, направляемых на полигоны, в два раза",
                        "Ликвидация наиболее опасных объектов накопленного вреда окружающей среде",
                        "Снижение выбросов опасных загрязняющих веществ, оказывающих наибольшее негативное воздействие на окружающую среду и здоровье человека, в два раза",
                        "Экологическое оздоровление водных объектов, включая реку Волгу, озера Байкал и Телецкое"},
                new int[]{0, 0, 1, 0}));


        questions.add(new Question("Расходы федерального бюджета на охрану окружающей среды в 2020 году:",
                new String[]{
                        "82,7 млрд руб.",
                        "158,1 млрд руб.",
                        "535,7 млрд руб.",
                        "301,7 млрд руб."},
                new int[]{0, 0, 0, 1}));


        questions.add(new Question("Основные индикаторы, влияющие на состояние окружающей среды в Российской Федерации:",
                new String[]{"Плотность населения",
                        "Выхлопы автотранспорта",
                        "Площадь зеленых насаждений",
                        "Выбросы предприятий",
                        "Свалочные газы"},
                new int[]{1, 1, 1, 1, 1}));
    }

}
