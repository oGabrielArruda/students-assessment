const conexao = require("../config/custom-mssql");

module.exports = app => {
  function execSQL(sql, resposta) {
    // funcao para executar a query passada como parametro
    global.conexao
      .request() // acessa a conexao
      .query(sql) // realiza o comando
      .then(resultado => resposta.json(resultado.recordset)) // retorna o restultado
      .catch(erro => resposta.json(erro)); // exibe o erro
  }

  app.get("/alunos/:ra", (req, res) => {
    global.conexao
    .request()
    .query(`select * from Alunos where ra = ${req.params.ra}`)
    .then(resultado => res.json(resultado.recordset[0]))
    .catch(err => res.json(err));
  });

  app.get("/resultados", async (req, res) => {
    execSQL("select * from Resultados", res);
  });

  app.post("/avaliar", async (req, res, next) => {
    let infos = req.body;
    const msgDeErro = await getMsgDeErro(infos);
    if (msgDeErro) res.status(404).send({ error: msgDeErro });

    let queries = `delete from Matriculas where ra = ${infos.ra} and cod = ${infos.cod};
        insert into Resultados values (${infos.ra}, ${infos.cod}, ${infos.nota}, ${infos.freq})`;

    await global.conexao
      .request()
      .query(queries)
      .catch(err => res.json(err));

      res.send("Sucesso!");
  });
};

async function getMsgDeErro(req) {
  let verificacoes = [
    {
      query: `select * from Alunos where RA = ${req.ra}`,
      msgErro: "RA de aluno inexistente!"
    },
    {
      query: `select * from Disciplinas where cod = ${req.cod}`,
      msgErro: "Código de disciplina inexistente!"
    },
    {
      query: `select * from Matriculas where ra = ${req.ra} and cod = ${req.cod}`,
      msgErro: "Aluno não matriculado na disciplina!"
    }
  ];

  for (let i = 0; i < verificacoes.length; i++) {
    try {
      let resposta = await global.conexao
        .request()
        .query(verificacoes[i].query);
      if (!resposta.recordset[0]) return verificacoes[i].msgErro;
    } catch (ex) {      
      console.log(ex);
    }
  }

  if (req.nota < 0 || req.nota > 10) return "Nota inválida!";
  if (req.freq < 0 || req.freq > 1) return "Frequência inválida!";

  return null;
}
