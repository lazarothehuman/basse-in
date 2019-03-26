create table transaction (
  id bigint(20) not null auto_increment,
  code varchar(255) default null,
  name varchar(255) default null,
  url varchar(200) default null,
  primary key (id),
  unique key code (code)
) engine=innodb default charset=latin1 ;

create table profile (
  id bigint(20) not null auto_increment,
  name varchar(255) default null,
  primary key (id),
  unique key uq_profile (name)
) engine=innodb default charset=latin1 ;

create table profile_transaction (
  profile_id bigint(20) not null,
  transaction_id bigint(20) not null,
  primary key (profile_id,transaction_id),
  key fk_prof_trans_profile (profile_id),
  key fk_prof_trans_transaction (transaction_id),
  constraint fk_prof_trans_profile foreign key (profile_id) references profile (id),
  constraint fk_prof_trans_transaction foreign key (transaction_id) references transaction (id)
) engine=innodb default charset=latin1 ;

create table user (
  id bigint(20) not null auto_increment,
  login varchar(255) not null,
  name varchar(255) not null,
  password varchar(255) not null,
  profile_id bigint(20) not null,
  primary key (id),
  unique key login (login),
  key fk_user_profile (profile_id),
  constraint fk_user_profile foreign key (profile_id) references profile (id)
) engine=innodb default charset=latin1;

INSERT INTO transaction (id,code,name,url) VALUES (null,'101','Cadastro de Perfis','/accesscontrol/profiles/profiles-list.zul');
INSERT INTO transaction (id,code,name,url) VALUES (null,'102','Cadastro de Usuários','/accesscontrol/users/users-list.zul');

insert into profile values (null, 'Administrador de Informática');

insert into profile_transaction values (1, 1);
insert into profile_transaction values (1, 2);

INSERT INTO user (id,login,name,password,profile_id) VALUES (null,'irshad','Irshad Amad Sidik Ismael','5HeK3YWsawsOhlzmJoHWsynm3ggqE2p/ztG3/f1xbmud5PnZly/7RTDW2XXQX54C',1);
