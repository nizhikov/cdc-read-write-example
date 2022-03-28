# CDC read write example

Пример чтения CDC данных в standalone приложении

NOTE: Данный пример использует internal API. 
Хорошим решением является запрос на доработку для создания public API standalone чтения сериализованных `BinaryObject`'ов.

## Куда смотреть в исходниках?

### ignite.xml

Конфиг Ignite'а с CDC consumer'ом, который пишет события в файлики.

### WriteToFileConsumer

Consumer - стандартным для java образом сохраняет данные в файлы.
Для его работы нужно после запуска Ignite ноды запустить `ignite-cdc.sh`

### Writer

Приложение - через тонкий клиент вставляет данные в кластер.
Нужно стартовать когда локально запущена и активирована нода игнайта.

### CdcDataReader

Читалка данных.

`binary_meta`, `marshaller` - папки, которые формируются в процессе работы ноды Игнайта.
Для корректной работы их нужно вручную перенести туда же где запускается приложение.

Пример вывода:

```text
[CdcDataReader] Start read data[root=/Users/sbt-izhikov-nv/tmp/cdc]
[CdcDataReader] Reading[file=5.bin, user=User{fio='John Connor', department=Department{id=1, name='Management'}, birthday=Sat Feb 28 00:00:00 MSK 3885}]
[CdcDataReader] Reading[file=4.bin, user=User{fio='Sarah Connor', department=Department{id=1, name='Management'}, birthday=Mon May 01 00:00:00 MSK 3865}]
[CdcDataReader] Reading[file=6.bin, user=User{fio='Kyle Reese', department=Department{id=1, name='Management'}, birthday=Tue Jul 05 00:00:00 MSK 3904}]
[CdcDataReader] Reading[file=3.bin, user=User{fio='Young and furious', department=Department{id=1, name='Management'}, birthday=Thu Jul 05 00:00:00 MSK 3900}]
[CdcDataReader] Reading[file=2.bin, user=User{fio='Vice president', department=Department{id=1, name='Management'}, birthday=Wed Mar 01 00:00:00 MSK 3871}]
[CdcDataReader] Reading[file=1.bin, user=User{fio='Big boss', department=Department{id=1, name='Management'}, birthday=Wed Feb 01 00:00:00 MSK 3860}]

Process finished with exit code 0
```