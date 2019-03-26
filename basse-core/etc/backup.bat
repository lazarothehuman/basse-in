mysqldump --user root --password --result-file /home/edrisse/workspace-ls/condominios-core/etc/condominios.sql condominios
mysqldump --user correctoras --password --result-file /home/losoft/backup-icon.sql correctoras

#Criar um backup da estrutura de tabelas apenas, proprio para o banco de testes
mysqldump --user root --password --result-file /opt/java/workspace-ls/correctoras-core/etc/empty_correctoras.sql correctoras -d

pause
