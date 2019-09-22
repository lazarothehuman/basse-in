mysqldump --user root --password --result-file /home/edrisse/workspace-ls/condominios-core/etc/condominios.sql condominios
mysqldump --user basse --password --result-file /home/losoft/backup-icon.sql basse

#Criar um backup da estrutura de tabelas apenas, proprio para o banco de testes
mysqldump --user root --password --result-file /opt/java/workspace-ls/basse-core/etc/empty_basse.sql basse -d

pause
