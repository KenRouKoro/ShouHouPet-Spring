package cn.korostudio.shouhoupetspring.tween;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Cubic;
import cn.korostudio.shouhoupetspring.err.Error;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TweenSystem {
    @Getter
    @Setter
    static protected int fps = 60;
    static protected Logger logger = LoggerFactory.getLogger(TweenSystem.class);
    protected TweenManager tweenManager;
    protected CopyOnWriteArrayList<TweenListener> tweenListeners = new CopyOnWriteArrayList<>();
    @Getter
    @Setter
    protected TweenEquation tweenMode = Cubic.INOUT;
    @Setter
    @Getter
    protected Component component;
    protected Thread runThread;
    @Getter
    protected boolean running = false;
    @Getter
    @Setter
    protected float time = 2f;
    @Setter
    @Getter
    protected int mode = TweenImplements.XY;
    protected boolean pause = false;
    @Getter
    @Setter
    protected boolean loop = false;
    protected boolean stop = false;
    @Getter
    @Setter
    protected float loopTime = 0;
    @Setter
    @Getter
    protected float[] arg;

    public void addTweenListener(TweenListener... tweenListeners) {
        this.tweenListeners.addAll(List.of(tweenListeners));
    }

    public void removeTweenListener(TweenListener... tweenListeners) {
        this.tweenListeners.removeAll(Collections.singleton(tweenListeners));
    }

    public void stop() {
        stop = true;
        running = false;
        List<BaseTween<?>> baseTweens = tweenManager.getObjects();
        for (BaseTween baseTween : baseTweens) {
            baseTween.free();
        }
        for (TweenListener tweenListener : tweenListeners) {
            tweenListener.stop();
        }
    }

    public void pause() {
        pause = true;
        List<BaseTween<?>> baseTweens = tweenManager.getObjects();
        for (BaseTween baseTween : baseTweens) {
            baseTween.pause();
        }
        for (TweenListener tweenListener : tweenListeners) {
            tweenListener.pause();
        }
    }

    public TweenSystem start() {
        if (!pause) {
            load();
            for (TweenListener tweenListener : tweenListeners) {
                tweenListener.start();
            }
            runThread.start();
            running = true;
            stop = false;
            return this;
        } else {
            pause = false;
            for (TweenListener tweenListener : tweenListeners) {
                tweenListener.start();
            }
            List<BaseTween<?>> baseTweens = tweenManager.getObjects();
            for (BaseTween baseTween : baseTweens) {
                baseTween.resume();
            }
            return this;
        }
    }

    protected void load() {
        try {
            Tween.registerAccessor(Component.class, new TweenImplements());
            tweenManager = new TweenManager();
            Tween tween = Tween.to(component, mode, time).ease(tweenMode).target(arg);
            if (loop) tween.repeat(Tween.INFINITY, loopTime);
            tween.start(tweenManager);
            runThread = new Thread(new RunThread());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class RunThread implements Runnable {

        public void run() {
            long lastMillis = System.currentTimeMillis();
            long deltaLastMillis = System.currentTimeMillis();
            running = true;
            while (running) {
                if (stop) return;
                long newMillis = System.currentTimeMillis();
                long sleep = 1000 / fps - (newMillis - lastMillis);
                lastMillis = newMillis;
                if (sleep > 1) {
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                long deltaNewMillis = System.currentTimeMillis();
                final float delta = (deltaNewMillis - deltaLastMillis) / 1000f;
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        tweenManager.update(delta);
                        List<BaseTween<?>> baseTweens = tweenManager.getObjects();
                        boolean canStop = true;
                        for (BaseTween baseTween : baseTweens) {
                            if (!baseTween.isFinished()) canStop = false;
                        }
                        if (canStop) running = false;
                        try {
                            ((JComponent) component).paintImmediately(component.getBounds());
                        } catch (Exception e) {
                            logger.debug("A AWT Component In Tween.");
                        }

                    });
                } catch (InterruptedException | InvocationTargetException ex) {
                    Error.error(TweenSystem.class, ex);
                }
                deltaLastMillis = newMillis;
            }
            running = false;
            if (!stop) for (TweenListener tweenListener : tweenListeners) {
                tweenListener.finish();
            }
        }
    }
}
