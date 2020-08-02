const express = require('express');
const bodyParser = require('body-parser');
const mssql = require('mssql');

mssql.connect("Server=regulus.cotuca.unicamp.br;Database=BD19169;User Id=BD19169;Password=BD19169;")
    .then(conn => global.conn = conn)
    .catch(err => console.log(err));

function execSQLQuery(sqlQry) {
    return global.conn.request().query(sqlQry);
}

const app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.get("/alunos/:ra", async (req, res) => {
    const { ra } = req.params;
    const resultQuery = await execSQLQuery(`select * from Alunos where ra = ${ra}`);
    return res.json(resultQuery.recordset[0]);
  });

app.post('/avaliar', async (req, res) => {
    const { ra, cod, nota, frequencia } = req.body;

    const alunoExistente = await execSQLQuery(`select * from Alunos where ra = ${ra}`);
    if (alunoExistente.recordset.length === 0) return res.status(400).json({ error: "RA inválido!"});

    const disciplinaExistente = await execSQLQuery(`select * from Disciplinas where cod = ${cod}`);
    if (disciplinaExistente.recordset.length === 0) return res.status(400).json({ error: "Código de discplina inválido!"});

    const matriculaExistente = await execSQLQuery(`select * from Matriculas where ra = ${ra} and cod = ${cod}`);
    if (matriculaExistente.recordset.length === 0) return res.status(400).json({ error: "Matrícula inexistente"});

    if (req.nota < 0 || req.nota > 10) return res.status(400).json({ error: "Nota inválida!" });

    if (req.freq < 0 || req.freq > 1) return res.status(400).json({ error: "Frequência inválida"});
    
    await execSQLQuery(`delete from Matriculas where ra = ${ra} and cod = ${cod}`)
    await execSQLQuery(`insert into Resultados values (${ra}, ${cod}, ${nota}, ${frequencia})`);
    return res.json(req.body);
});

app.listen('3000', () => {
    console.log('Rodando!');
});