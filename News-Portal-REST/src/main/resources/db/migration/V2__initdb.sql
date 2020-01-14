# username: admin
# password: admin
insert into `user`(`account_non_locked`, `active`, `password`, `username`)
values (1, 1, '$2a$11$5g8fLZ0V6kvlIm19pF.akewy7geStR2Qn4DRjVv/7.Xtef/ugfvz.', 'admin');
insert into `user_role` (`user_id`, `roles`)
values (1, 'ADMINISTRATOR'), (1, 'USER');

# Account is blocked. You must change the value of the account_non_locked field to 1 to unlock the account
# username: user
# password: user
insert into `user`(`account_non_locked`, `active`, `password`, `username`)
values (0, 1, '$2a$11$1mIfui08VsO6nCPIwLxKd.6f0oUTn.9wM73hFeKKeX9gnHhdb1KQK', 'user');
insert into `user_role` (`user_id`, `roles`)
values (2, 'USER');