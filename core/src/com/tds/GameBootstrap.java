package com.tds;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tds.input.InputHandler;
import com.tds.input.InputService;
import com.tds.score.GdxPreferencesScoreRepository;
import com.tds.score.ScoreRepository;
import com.tds.screen.OrthographicRenderStrategy;
import com.tds.screen.RenderStrategy;

import java.util.function.Supplier;

/**
 * Bootstraps the game by constructing concrete dependencies.
 *
 * <p>Dependencies can be overridden for testing purposes via the "with" methods.</p>
 */
public class GameBootstrap {

    private Supplier<SpriteBatch> batchSupplier = SpriteBatch::new;
    private Supplier<AssetManager> assetManagerSupplier = AssetManager::new;
    private Supplier<InputService> inputServiceSupplier = InputHandler::new;
    private Supplier<ScoreRepository> scoreRepositorySupplier = GdxPreferencesScoreRepository::new;
    private Supplier<RenderStrategy> renderStrategySupplier = () -> new OrthographicRenderStrategy(800, 600);

    public GameBootstrap withSpriteBatch(SpriteBatch batch) {
        this.batchSupplier = () -> batch;
        return this;
    }

    public GameBootstrap withAssetManager(AssetManager assetManager) {
        this.assetManagerSupplier = () -> assetManager;
        return this;
    }

    public GameBootstrap withInputService(InputService input) {
        this.inputServiceSupplier = () -> input;
        return this;
    }

    public GameBootstrap withScoreRepository(ScoreRepository repo) {
        this.scoreRepositorySupplier = () -> repo;
        return this;
    }

    public GameBootstrap withRenderStrategy(RenderStrategy strategy) {
        this.renderStrategySupplier = () -> strategy;
        return this;
    }

    /**
     * Construct a fully wired {@link TDS} instance.
     *
     * @return configured game instance
     */
    public TDS bootstrap() {
        return new TDS(
            batchSupplier,
            assetManagerSupplier.get(),
            inputServiceSupplier.get(),
            scoreRepositorySupplier.get(),
            renderStrategySupplier.get()
        );
    }
}

