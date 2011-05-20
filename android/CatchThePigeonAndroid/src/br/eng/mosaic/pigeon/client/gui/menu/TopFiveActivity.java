package br.eng.mosaic.pigeon.client.gui.menu;

import java.util.List;

import br.eng.mosaic.pigeon.client.infra.persistence.ScoreRepository;
import br.eng.mosaic.pigeon.client.infra.persistence.ScoreRepositoryScript;
import br.eng.mosaic.pigeon.common.dto.Score;
import android.app.ListActivity;
import android.os.Bundle;

public class TopFiveActivity extends ListActivity {
	
	public static ScoreRepository repository;

	private List<Score> scores;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		repository = new ScoreRepositoryScript(this);
		updateList();
	}
	
	protected void updateList() {
		scores = repository.listTopFive();

		setListAdapter(new ScoreListAdapter(this, scores));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Fecha o banco
		repository.close();
	}
	
	

}
