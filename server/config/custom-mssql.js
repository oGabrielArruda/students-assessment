var mssql = require('mssql');
const config = {
    user: 'BD19169',
    password: 'BD19169',
    server: 'regulus.cotuca.unicamp.br',
    database: 'BD19169',
    options: {
        encrypt: false,
    }
};
mssql.connect(config)
    .then(conexao => global.conexao = conexao)
    .catch(erro => console.log(erro));

module.exports = mssql;