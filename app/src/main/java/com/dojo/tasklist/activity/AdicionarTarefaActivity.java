package com.dojo.tasklist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dojo.tasklist.R;
import com.dojo.tasklist.helper.TarefaDAO;
import com.dojo.tasklist.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.textTarefa);

        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        if(tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemSalvar:
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if(tarefaAtual != null){
                    String nomeTarefa = editTarefa.getText().toString();
                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());
                        if(tarefaDAO.atualizar(tarefa)){
                            finish();
                            Toast.makeText(AdicionarTarefaActivity.this," tarefa atualizar com sucesso!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AdicionarTarefaActivity.this,"Erro ao atualizar tarefa!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    String nomeTarefa = editTarefa.getText().toString();
                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);

                        if(tarefaDAO.salvar(tarefa)){
                            finish();
                            Toast.makeText(AdicionarTarefaActivity.this," tarefa salva com sucesso!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AdicionarTarefaActivity.this,"Erro ao salvar tarefa!",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(AdicionarTarefaActivity.this,"Nenhuma tarefa a ser salva!",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}