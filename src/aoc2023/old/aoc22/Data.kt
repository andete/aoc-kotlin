package be.damad.aoc2023.aoc22

val aoc22data = """2,0,67~3,0,67
7,5,107~8,5,107
4,0,272~4,2,272
7,8,76~7,8,78
9,4,118~9,7,118
3,0,22~5,0,22
1,6,157~1,9,157
4,2,121~4,3,121
0,7,302~0,9,302
1,0,47~1,1,47
2,4,142~3,4,142
5,2,150~5,2,152
9,7,146~9,9,146
7,5,348~7,8,348
2,6,247~3,6,247
2,7,289~5,7,289
2,6,94~2,9,94
3,7,110~5,7,110
4,7,353~5,7,353
2,8,214~2,8,216
2,2,310~2,4,310
4,4,190~5,4,190
9,4,210~9,6,210
8,1,329~8,3,329
0,8,263~4,8,263
4,6,155~4,6,157
8,6,247~8,9,247
6,5,323~6,8,323
0,2,314~0,5,314
0,0,230~0,2,230
1,5,161~4,5,161
3,6,339~3,9,339
9,6,251~9,7,251
2,3,309~4,3,309
8,7,76~8,9,76
2,5,8~2,7,8
6,5,334~6,8,334
5,5,209~5,7,209
3,4,43~3,4,45
7,6,188~7,9,188
2,3,345~5,3,345
8,0,196~8,2,196
7,7,294~7,9,294
5,5,111~5,7,111
4,5,201~7,5,201
4,6,113~7,6,113
8,4,22~8,5,22
5,0,313~7,0,313
5,4,207~5,5,207
0,3,2~0,5,2
1,9,261~1,9,263
4,3,285~4,6,285
8,1,256~9,1,256
5,8,243~7,8,243
8,5,342~8,6,342
0,7,320~0,7,322
6,2,223~7,2,223
0,5,107~0,7,107
6,7,252~6,8,252
6,7,331~8,7,331
1,2,294~1,2,294
1,0,214~1,2,214
2,5,40~4,5,40
6,7,282~6,9,282
8,3,194~8,5,194
9,5,200~9,5,201
1,2,312~4,2,312
5,0,57~8,0,57
5,6,94~5,7,94
4,3,184~4,5,184
0,6,2~1,6,2
2,7,110~2,9,110
9,3,233~9,3,236
4,1,185~5,1,185
1,2,274~2,2,274
8,6,321~8,9,321
8,4,2~8,4,5
0,3,295~0,6,295
5,2,147~5,3,147
0,4,316~0,5,316
5,3,144~8,3,144
5,1,228~9,1,228
6,4,142~7,4,142
7,2,202~7,4,202
6,2,306~6,4,306
0,2,323~0,4,323
2,3,327~2,6,327
5,7,141~5,9,141
6,6,246~9,6,246
1,5,135~4,5,135
1,1,132~1,1,133
3,5,244~4,5,244
3,4,89~3,5,89
3,5,189~3,8,189
9,0,231~9,2,231
7,4,34~8,4,34
2,0,158~2,2,158
4,3,86~4,4,86
2,9,234~4,9,234
1,3,18~1,3,20
2,4,107~2,6,107
5,7,183~7,7,183
0,0,5~0,2,5
5,0,74~7,0,74
6,0,260~9,0,260
0,3,200~0,6,200
8,4,14~8,7,14
2,8,83~2,8,83
2,6,42~2,9,42
6,0,45~6,2,45
8,2,195~8,3,195
7,7,197~9,7,197
6,0,115~9,0,115
1,4,195~4,4,195
7,1,167~7,3,167
7,5,207~9,5,207
2,4,90~4,4,90
6,8,6~6,9,6
6,2,359~6,2,361
6,1,184~6,1,187
1,3,76~1,4,76
0,8,182~2,8,182
3,0,70~3,0,71
5,4,11~5,7,11
7,4,294~9,4,294
6,1,276~6,4,276
7,9,18~9,9,18
2,9,175~6,9,175
2,3,320~2,6,320
8,5,79~8,8,79
0,1,93~4,1,93
2,0,66~2,2,66
0,3,154~0,6,154
5,2,322~7,2,322
8,4,188~8,7,188
7,3,85~9,3,85
0,2,56~2,2,56
3,1,88~5,1,88
7,5,26~7,8,26
5,0,127~5,3,127
2,4,35~3,4,35
2,6,156~2,8,156
4,8,187~6,8,187
5,1,47~5,3,47
7,2,351~7,4,351
6,3,3~6,3,6
1,0,215~2,0,215
1,5,55~2,5,55
5,2,222~7,2,222
6,1,189~6,2,189
1,3,340~5,3,340
1,4,302~1,7,302
2,0,298~2,0,299
5,0,226~5,2,226
1,1,45~1,3,45
3,8,112~6,8,112
9,5,253~9,8,253
2,3,152~2,5,152
7,3,29~8,3,29
7,4,296~7,4,297
5,1,223~5,2,223
0,8,2~3,8,2
5,1,51~7,1,51
7,2,89~7,2,92
0,1,323~1,1,323
6,2,292~6,3,292
3,0,116~5,0,116
2,5,223~5,5,223
0,2,86~3,2,86
4,7,128~6,7,128
6,2,268~6,4,268
2,6,187~2,9,187
0,7,256~3,7,256
5,9,308~7,9,308
0,0,7~0,2,7
1,9,70~5,9,70
4,5,147~4,7,147
6,7,13~6,9,13
7,4,37~7,6,37
0,5,101~2,5,101
2,1,271~5,1,271
2,7,354~4,7,354
9,0,42~9,2,42
2,4,314~5,4,314
3,0,279~3,0,279
3,8,310~6,8,310
2,6,256~2,6,258
9,3,122~9,5,122
7,7,119~7,9,119
2,8,126~3,8,126
1,0,139~1,3,139
9,5,204~9,7,204
7,7,320~9,7,320
5,7,299~5,8,299
7,0,24~7,3,24
8,6,172~8,8,172
2,5,121~4,5,121
6,4,176~6,4,178
4,4,319~4,6,319
7,4,349~7,7,349
4,3,123~4,4,123
9,0,101~9,3,101
2,7,246~4,7,246
2,1,114~4,1,114
5,5,147~5,7,147
4,4,207~4,4,208
9,8,219~9,8,220
4,2,19~4,5,19
2,8,15~2,8,16
1,3,119~4,3,119
7,0,296~8,0,296
9,6,338~9,9,338
5,0,320~7,0,320
1,5,164~1,5,165
7,6,283~9,6,283
9,7,121~9,8,121
4,1,343~5,1,343
1,1,314~1,3,314
1,5,156~3,5,156
6,0,2~6,2,2
2,0,25~2,2,25
7,7,2~7,7,4
5,2,32~7,2,32
0,9,328~2,9,328
4,0,182~4,3,182
4,3,186~4,3,187
5,1,89~8,1,89
2,4,178~5,4,178
4,1,40~4,4,40
1,3,75~1,5,75
6,7,171~8,7,171
8,7,333~9,7,333
1,2,81~1,2,83
2,3,155~4,3,155
3,4,235~3,7,235
0,4,248~1,4,248
1,2,160~3,2,160
6,6,111~6,7,111
4,3,238~4,5,238
2,2,217~4,2,217
6,6,161~6,8,161
2,1,202~2,3,202
8,0,330~8,3,330
5,5,37~5,9,37
1,1,31~1,3,31
4,2,248~7,2,248
5,6,173~6,6,173
1,5,108~1,7,108
7,2,161~9,2,161
0,4,259~0,6,259
4,9,285~7,9,285
1,5,247~1,8,247
4,3,83~4,5,83
6,3,349~6,6,349
7,4,2~7,5,2
1,7,335~1,9,335
4,3,47~4,3,50
0,5,346~2,5,346
1,6,129~3,6,129
3,1,110~5,1,110
5,4,354~5,7,354
7,7,223~7,8,223
1,0,152~1,2,152
5,0,213~5,2,213
6,1,333~6,1,335
6,3,75~8,3,75
6,3,178~8,3,178
7,1,182~8,1,182
5,4,5~7,4,5
6,2,350~6,3,350
2,5,98~4,5,98
2,0,288~5,0,288
3,7,177~5,7,177
3,1,1~4,1,1
2,2,107~2,2,107
6,5,83~8,5,83
8,4,296~8,6,296
4,7,7~6,7,7
9,5,157~9,8,157
9,6,5~9,8,5
9,5,236~9,7,236
1,3,194~3,3,194
2,1,272~2,2,272
7,3,147~8,3,147
3,1,199~3,1,201
6,7,199~8,7,199
4,4,222~4,6,222
0,8,250~3,8,250
7,1,299~7,3,299
3,6,183~3,9,183
5,3,79~5,4,79
9,2,47~9,5,47
8,8,183~8,9,183
0,4,40~0,7,40
2,5,291~2,7,291
3,4,293~3,5,293
2,3,154~2,6,154
6,0,177~9,0,177
2,2,71~3,2,71
4,8,96~4,8,98
5,1,264~5,3,264
2,4,339~2,6,339
0,4,162~3,4,162
5,3,17~5,5,17
5,4,134~5,7,134
4,2,16~6,2,16
2,2,35~4,2,35
2,2,307~2,4,307
7,0,217~9,0,217
6,4,336~8,4,336
2,7,330~2,7,332
6,3,353~6,5,353
6,5,303~7,5,303
1,5,348~1,7,348
9,0,17~9,3,17
7,3,35~9,3,35
8,3,148~8,3,149
5,5,276~7,5,276
0,4,90~0,4,90
7,6,57~7,8,57
6,3,307~9,3,307
0,3,159~0,3,161
4,4,159~6,4,159
4,1,145~4,4,145
6,7,319~9,7,319
7,2,177~9,2,177
0,1,231~0,1,233
6,5,109~6,6,109
5,9,140~7,9,140
0,7,258~0,7,260
3,4,93~4,4,93
3,6,10~5,6,10
0,8,256~0,9,256
0,4,325~0,4,327
3,5,9~6,5,9
8,2,167~8,4,167
3,1,332~3,3,332
3,3,93~6,3,93
4,0,106~4,2,106
3,1,44~3,1,46
2,4,32~4,4,32
5,0,112~5,2,112
6,3,301~7,3,301
2,4,315~2,4,317
8,4,222~9,4,222
1,1,79~1,4,79
3,4,33~3,5,33
2,5,122~2,5,123
0,0,286~3,0,286
9,4,4~9,5,4
3,1,349~4,1,349
9,5,27~9,6,27
0,0,76~2,0,76
6,4,182~6,6,182
9,2,38~9,5,38
0,8,65~2,8,65
0,7,3~0,7,6
5,3,315~7,3,315
2,7,181~2,9,181
7,5,177~9,5,177
0,3,99~2,3,99
2,4,129~4,4,129
3,5,259~3,7,259
2,1,277~4,1,277
4,6,57~5,6,57
2,0,117~3,0,117
6,2,175~6,5,175
2,6,56~4,6,56
7,7,107~7,7,110
5,1,14~5,3,14
5,1,219~5,3,219
3,7,51~6,7,51
5,2,193~5,4,193
9,6,249~9,9,249
7,8,181~9,8,181
6,0,22~8,0,22
1,2,239~1,5,239
1,0,299~1,0,300
1,5,107~1,7,107
5,7,315~7,7,315
4,9,103~6,9,103
4,8,3~4,9,3
9,4,61~9,5,61
0,0,27~3,0,27
1,8,319~4,8,319
2,7,17~4,7,17
0,0,49~0,2,49
6,6,289~6,8,289
5,7,285~7,7,285
1,3,159~1,5,159
2,5,103~2,7,103
4,5,288~5,5,288
6,4,26~6,6,26
9,3,174~9,5,174
5,7,77~7,7,77
3,1,180~6,1,180
2,0,159~5,0,159
4,1,199~4,2,199
2,2,108~2,2,109
5,4,153~7,4,153
9,6,205~9,8,205
5,2,281~7,2,281
3,9,119~6,9,119
5,0,128~8,0,128
1,4,112~1,6,112
8,3,2~8,3,4
2,6,72~2,8,72
7,2,235~9,2,235
4,7,116~4,9,116
5,2,227~8,2,227
4,6,107~4,8,107
0,7,186~3,7,186
5,2,268~5,4,268
6,6,275~6,6,275
4,8,14~6,8,14
7,7,59~9,7,59
2,0,268~2,3,268
9,5,239~9,5,241
6,5,166~8,5,166
2,3,59~2,3,61
3,4,41~4,4,41
0,1,155~3,1,155
3,3,286~3,4,286
6,2,172~6,3,172
6,5,312~6,8,312
2,3,181~2,6,181
5,2,304~7,2,304
0,9,52~1,9,52
5,5,184~7,5,184
1,0,207~1,2,207
2,0,311~4,0,311
6,3,313~8,3,313
7,0,259~7,2,259
9,5,194~9,7,194
1,1,294~4,1,294
0,7,92~2,7,92
4,8,246~5,8,246
5,5,327~5,7,327
7,8,194~9,8,194
1,6,156~1,8,156
6,2,356~6,4,356
2,1,228~2,2,228
6,2,76~6,3,76
1,2,69~4,2,69
0,9,51~2,9,51
0,9,188~2,9,188
8,7,117~8,9,117
0,5,91~2,5,91
5,4,100~7,4,100
4,9,176~6,9,176
2,4,72~3,4,72
5,3,115~7,3,115
7,6,190~9,6,190
6,4,324~9,4,324
8,5,190~9,5,190
3,3,311~3,4,311
4,4,50~7,4,50
4,6,286~4,6,288
3,6,333~3,6,334
8,1,102~8,2,102
2,3,55~3,3,55
2,8,68~4,8,68
6,0,172~6,0,174
8,2,206~8,4,206
9,7,336~9,9,336
9,6,115~9,7,115
7,3,171~9,3,171
8,0,60~9,0,60
3,0,194~3,2,194
0,8,48~3,8,48
0,2,206~1,2,206
6,1,212~8,1,212
5,4,137~5,4,139
2,6,67~5,6,67
8,5,110~8,5,112
4,4,332~4,5,332
3,9,149~5,9,149
3,1,283~3,3,283
4,1,296~4,1,298
0,5,210~0,8,210
4,7,231~4,9,231
5,8,321~6,8,321
1,1,42~5,1,42
8,9,250~9,9,250
0,2,147~0,5,147
9,9,80~9,9,81
8,0,130~8,2,130
5,9,230~5,9,232
2,0,240~5,0,240
6,8,74~9,8,74
6,5,177~6,8,177
6,9,7~8,9,7
5,6,80~5,9,80
3,3,226~3,5,226
5,1,341~6,1,341
1,3,166~1,5,166
4,6,327~4,7,327
7,0,315~9,0,315
4,1,234~5,1,234
9,6,124~9,8,124
2,4,331~4,4,331
9,7,256~9,8,256
4,3,222~5,3,222
5,0,255~5,2,255
4,4,320~6,4,320
5,8,183~7,8,183
0,4,149~0,6,149
1,2,313~1,4,313
9,4,26~9,6,26
5,2,96~5,5,96
7,6,215~9,6,215
7,7,350~7,9,350
1,3,317~1,3,319
2,8,128~2,8,130
9,3,102~9,4,102
1,6,91~3,6,91
5,0,181~5,1,181
6,2,47~6,5,47
3,1,338~5,1,338
4,7,192~6,7,192
4,1,246~4,4,246
0,4,145~2,4,145
4,7,142~7,7,142
6,1,209~8,1,209
4,1,258~7,1,258
1,6,39~3,6,39
1,6,186~1,6,187
2,8,12~2,9,12
8,0,49~8,1,49
7,2,261~7,5,261
0,8,264~3,8,264
0,3,141~1,3,141
7,5,113~8,5,113
6,1,279~6,3,279
7,4,278~7,5,278
4,5,48~4,7,48
4,5,173~4,7,173
3,2,116~4,2,116
0,9,106~2,9,106
1,0,141~3,0,141
0,1,188~0,3,188
3,8,43~6,8,43
7,5,217~7,7,217
7,5,220~7,7,220
8,0,40~8,1,40
4,7,28~4,7,30
6,1,181~8,1,181
3,2,146~6,2,146
3,4,347~4,4,347
9,1,233~9,1,235
4,1,232~4,2,232
1,0,166~1,0,168
9,1,84~9,4,84
1,4,148~1,5,148
0,2,317~0,4,317
4,6,95~4,8,95
1,5,47~1,7,47
7,3,78~7,4,78
4,8,109~6,8,109
2,7,151~5,7,151
4,2,176~4,4,176
0,1,81~3,1,81
1,4,160~4,4,160
9,0,41~9,2,41
0,4,136~2,4,136
2,2,305~2,4,305
8,2,334~8,3,334
4,4,308~6,4,308
7,2,189~9,2,189
7,2,42~7,4,42
6,9,323~8,9,323
0,4,165~0,6,165
4,6,315~4,8,315
1,3,170~3,3,170
2,2,164~2,5,164
0,2,203~2,2,203
4,2,21~4,6,21
5,5,279~8,5,279
0,7,301~1,7,301
0,9,2~2,9,2
0,7,114~0,9,114
5,5,294~5,8,294
4,2,332~6,2,332
8,5,54~8,8,54
2,4,139~4,4,139
5,1,97~8,1,97
6,3,18~8,3,18
0,4,19~0,6,19
9,4,144~9,7,144
2,4,105~2,6,105
1,6,307~4,6,307
6,1,183~6,3,183
4,3,57~6,3,57
2,0,275~4,0,275
5,1,203~6,1,203
7,9,78~9,9,78
4,1,81~7,1,81
9,3,44~9,4,44
8,0,173~8,2,173
4,7,233~6,7,233
2,2,7~2,5,7
1,4,106~3,4,106
3,6,245~6,6,245
5,4,195~7,4,195
8,3,100~8,5,100
8,1,98~8,1,100
1,3,204~1,4,204
1,3,78~1,4,78
4,2,103~4,4,103
9,2,326~9,4,326
5,6,211~7,6,211
6,1,171~8,1,171
3,8,143~5,8,143
9,5,155~9,7,155
0,4,348~0,5,348
6,0,18~6,2,18
3,7,104~5,7,104
5,6,183~7,6,183
1,1,48~3,1,48
4,6,281~4,6,282
0,5,10~2,5,10
0,4,260~0,6,260
4,0,200~6,0,200
9,3,50~9,5,50
1,0,163~1,3,163
6,5,227~6,8,227
0,1,223~0,4,223
5,1,25~7,1,25
5,7,197~5,9,197
0,7,248~1,7,248
8,7,179~8,9,179
6,6,78~9,6,78
3,8,12~5,8,12
2,1,14~2,3,14
4,4,311~6,4,311
2,1,117~4,1,117
5,7,43~6,7,43
8,2,228~8,2,230
2,2,6~2,5,6
4,7,158~7,7,158
4,6,181~4,8,181
5,7,133~5,9,133
1,2,104~4,2,104
0,2,228~0,3,228
2,0,147~5,0,147
6,9,169~8,9,169
7,6,51~9,6,51
5,0,259~5,2,259
1,4,104~2,4,104
2,3,311~2,4,311
2,3,352~5,3,352
8,2,316~8,4,316
1,8,266~2,8,266
7,7,296~7,9,296
0,6,223~2,6,223
3,8,73~3,9,73
2,5,89~2,7,89
5,4,75~5,4,77
2,9,45~2,9,48
5,0,237~5,2,237
1,0,29~1,2,29
1,6,52~1,6,54
4,8,236~6,8,236
6,3,9~8,3,9
3,6,325~3,7,325
4,1,285~4,2,285
0,7,212~0,7,213
3,1,204~3,4,204
5,4,186~5,5,186
9,4,211~9,7,211
0,0,335~3,0,335
5,7,103~7,7,103
3,8,265~3,9,265
4,6,326~6,6,326
2,1,15~2,2,15
2,2,65~2,3,65
4,4,68~6,4,68
5,0,211~5,3,211
6,9,105~8,9,105
4,2,140~4,4,140
2,3,323~2,5,323
7,1,160~7,4,160
8,7,318~8,9,318
0,9,109~2,9,109
6,2,327~6,4,327
1,4,29~3,4,29
7,4,291~7,7,291
0,5,297~1,5,297
1,5,298~1,6,298
1,0,328~2,0,328
5,4,219~8,4,219
6,4,273~6,6,273
6,3,223~6,5,223
7,1,183~9,1,183
6,0,48~8,0,48
0,1,87~0,4,87
6,4,164~6,4,164
4,2,201~4,3,201
1,3,338~3,3,338
8,3,320~8,5,320
2,5,147~3,5,147
4,1,16~7,1,16
2,8,157~3,8,157
9,2,124~9,3,124
4,7,92~6,7,92
4,3,35~4,3,38
2,9,104~4,9,104
0,1,320~0,3,320
1,9,241~4,9,241
7,0,7~7,0,9
0,3,221~0,6,221
2,7,136~3,7,136
8,7,191~8,9,191
3,5,70~3,6,70
4,0,295~4,3,295
2,8,69~2,8,71
5,0,284~6,0,284
2,5,86~4,5,86
2,2,191~4,2,191
0,6,9~4,6,9
0,3,249~0,5,249
0,6,205~0,8,205
2,3,289~4,3,289
3,5,318~3,8,318
0,2,73~0,3,73
4,4,67~4,5,67
7,4,165~7,4,165
4,9,236~4,9,238
4,3,59~5,3,59
6,1,354~6,3,354
0,0,298~0,1,298
4,9,118~4,9,118
0,3,226~0,7,226
1,4,333~1,7,333
8,0,213~9,0,213
5,1,272~7,1,272
7,3,47~7,3,47
4,4,329~6,4,329
9,1,51~9,1,54
4,8,191~4,8,192
1,1,22~1,3,22
1,2,209~3,2,209
4,2,209~4,2,211
2,3,209~2,6,209
6,7,53~6,7,54
1,0,72~1,0,73
0,6,18~0,8,18
5,2,98~7,2,98
3,4,150~3,6,150
1,4,16~1,7,16
8,0,29~9,0,29
8,1,327~8,3,327
5,3,143~5,5,143
8,0,263~8,0,264
3,4,69~5,4,69
5,7,105~5,7,105
8,1,22~8,2,22
5,4,150~5,6,150
1,8,158~1,9,158
3,6,308~3,6,310
8,2,210~9,2,210
6,9,15~8,9,15
1,9,322~2,9,322
4,0,47~4,0,47
1,6,42~1,6,44
3,4,57~3,6,57
0,9,110~0,9,112
2,2,208~5,2,208
6,2,186~7,2,186
3,9,226~4,9,226
5,9,77~8,9,77
0,2,54~2,2,54
0,5,319~0,8,319
9,5,218~9,8,218
5,9,136~8,9,136
6,6,286~9,6,286
6,7,52~6,9,52
0,1,157~0,3,157
2,7,190~2,9,190
1,8,79~2,8,79
6,2,207~9,2,207
8,4,323~9,4,323
0,2,3~3,2,3
1,2,182~2,2,182
7,4,334~9,4,334
7,2,306~7,3,306
7,7,297~7,9,297
4,3,324~7,3,324
5,1,149~5,1,149
0,2,322~3,2,322
8,5,285~9,5,285
0,2,39~0,4,39
1,9,67~2,9,67
3,5,186~4,5,186
6,9,193~8,9,193
0,4,203~2,4,203
6,5,115~8,5,115
9,7,113~9,9,113
8,5,217~8,7,217
0,1,46~1,1,46
4,3,243~4,3,245
6,0,117~8,0,117
1,0,290~3,0,290
1,1,335~3,1,335
6,4,313~6,5,313
6,5,20~8,5,20
7,7,289~7,9,289
2,5,215~5,5,215
3,2,277~3,4,277
8,1,288~8,4,288
1,3,118~3,3,118
3,1,179~5,1,179
8,6,3~8,8,3
0,6,253~0,8,253
0,5,55~0,7,55
4,0,58~6,0,58
9,2,59~9,5,59
2,9,144~2,9,144
4,8,249~6,8,249
4,9,311~4,9,312
0,5,13~1,5,13
6,7,207~9,7,207
3,2,174~6,2,174
4,4,80~4,6,80
6,2,99~9,2,99
0,5,257~0,7,257
9,0,5~9,2,5
0,3,103~0,4,103
4,8,234~4,8,235
3,6,125~3,8,125
6,2,302~6,5,302
3,0,196~5,0,196
4,5,220~4,6,220
3,5,49~5,5,49
9,5,255~9,5,258
2,9,308~4,9,308
2,3,349~2,5,349
9,0,215~9,3,215
1,5,251~1,5,253
7,5,242~7,8,242
2,3,315~4,3,315
0,8,212~2,8,212
4,3,251~4,4,251
9,0,49~9,2,49
1,6,49~1,7,49
3,6,59~3,8,59
2,4,27~4,4,27
4,5,351~4,8,351
3,6,155~3,6,157
6,6,197~6,8,197
9,7,213~9,7,214
6,2,15~7,2,15
5,0,286~5,2,286
0,0,51~0,2,51
1,2,346~3,2,346
2,9,307~5,9,307
2,6,299~2,8,299
7,3,74~7,5,74
6,4,220~6,5,220
0,6,254~2,6,254
3,1,275~3,3,275
5,4,25~5,4,28
2,3,10~2,3,11
4,3,212~4,6,212
3,9,259~3,9,261
2,2,92~2,4,92
7,7,202~7,9,202
4,1,303~4,1,306
3,9,223~6,9,223
3,4,198~3,4,199
0,4,43~0,5,43
4,5,346~7,5,346
0,1,326~2,1,326
0,7,54~0,9,54
4,5,343~6,5,343
2,7,253~2,8,253
6,8,315~8,8,315
3,1,233~3,1,233
7,9,24~8,9,24
2,7,327~3,7,327
4,6,167~6,6,167
5,1,206~7,1,206
1,0,284~3,0,284
7,2,325~9,2,325
3,3,228~3,3,229
8,2,201~8,5,201
4,9,146~5,9,146
7,5,110~7,5,111
0,4,68~3,4,68
0,5,299~0,9,299
4,1,127~4,3,127
4,1,301~4,3,301
1,3,17~2,3,17
8,3,339~8,5,339
9,1,292~9,3,292
3,1,342~3,3,342
1,3,350~3,3,350
0,6,331~3,6,331
9,3,119~9,5,119
6,4,163~8,4,163
0,1,92~1,1,92
7,3,314~9,3,314
7,6,161~7,8,161
5,0,78~5,1,78
0,0,296~2,0,296
3,8,208~4,8,208
1,2,105~4,2,105
3,6,36~5,6,36
9,2,164~9,3,164
5,1,44~7,1,44
1,7,75~3,7,75
6,6,106~6,8,106
0,0,178~1,0,178
6,1,37~7,1,37
1,5,229~5,5,229
4,2,33~4,5,33
0,6,108~0,8,108
3,1,337~3,3,337
3,8,341~3,9,341
2,3,148~2,5,148
2,1,274~4,1,274
2,1,206~2,4,206
9,1,87~9,3,87
0,2,185~2,2,185
0,3,325~2,3,325
7,2,236~7,5,236
6,6,5~6,9,5
2,3,156~4,3,156
3,1,280~3,2,280
3,0,144~5,0,144
6,2,323~6,2,325
2,8,76~3,8,76
8,4,282~8,5,282
0,0,142~1,0,142
2,2,277~2,4,277
1,0,293~1,3,293
5,8,323~5,9,323
5,3,12~8,3,12
4,5,119~4,8,119
0,8,207~4,8,207
4,6,328~4,7,328
7,3,290~8,3,290
8,2,332~8,5,332
0,3,328~0,4,328
8,4,191~8,4,193
0,6,152~0,9,152
3,9,8~3,9,10
0,4,37~2,4,37
3,8,15~3,8,17
7,9,21~9,9,21
1,6,90~2,6,90
5,3,187~5,5,187
5,8,145~5,8,146
3,4,1~3,7,1
2,5,292~4,5,292
7,0,94~7,3,94
0,4,247~3,4,247
0,3,101~1,3,101
1,7,326~3,7,326
4,6,152~6,6,152
4,2,141~4,3,141
2,1,329~4,1,329
9,2,232~9,4,232
2,6,293~2,8,293
4,5,246~5,5,246
5,4,237~7,4,237
4,0,5~7,0,5
2,5,153~5,5,153
9,0,56~9,1,56
0,1,166~1,1,166
7,0,277~7,1,277
0,1,205~0,4,205
2,0,281~6,0,281
9,7,242~9,9,242
4,5,176~5,5,176
5,0,307~5,2,307
0,6,57~0,7,57
6,2,69~6,5,69
3,0,297~3,2,297
1,1,18~3,1,18
7,2,118~7,2,120
7,3,97~9,3,97
7,3,17~7,6,17
2,3,149~2,6,149
0,6,206~0,6,206
7,8,1~9,8,1
6,8,192~8,8,192
1,1,284~4,1,284
5,1,253~8,1,253
4,9,153~4,9,155
4,4,111~4,7,111
3,4,34~3,7,34
9,4,23~9,7,23
9,6,323~9,7,323
3,1,84~3,1,86
7,6,73~7,8,73
0,2,150~2,2,150
4,1,142~4,3,142
1,1,196~4,1,196
1,2,240~3,2,240
9,1,3~9,3,3
1,2,95~3,2,95
5,5,357~6,5,357
8,0,123~8,3,123
2,4,65~2,7,65
7,1,169~9,1,169
1,0,69~2,0,69
3,7,216~6,7,216
2,7,12~4,7,12
9,4,234~9,5,234
7,7,190~7,9,190
8,0,199~9,0,199
2,4,12~2,5,12
6,7,136~6,8,136
1,3,56~2,3,56
0,3,64~2,3,64
2,8,269~5,8,269
0,0,52~2,0,52
4,1,54~4,3,54
1,2,109~1,4,109
3,1,230~4,1,230
3,1,147~5,1,147
4,2,1~4,4,1
9,0,22~9,1,22
0,0,289~0,1,289
9,0,212~9,2,212
4,3,239~4,3,241
0,0,171~2,0,171
0,0,173~0,0,176
3,9,151~6,9,151
1,8,325~1,9,325
4,5,89~4,8,89
2,9,143~5,9,143
9,4,142~9,5,142
3,4,144~3,5,144
9,4,3~9,5,3
7,6,155~7,9,155
5,3,106~6,3,106
9,0,180~9,2,180
6,1,284~6,3,284
4,8,287~4,9,287
3,6,61~5,6,61
2,1,346~4,1,346
1,4,253~1,4,253
8,2,11~8,5,11
3,5,172~3,8,172
0,0,342~0,3,342
7,0,275~7,2,275
9,0,20~9,3,20
8,4,86~8,5,86
8,2,170~8,5,170
7,2,311~8,2,311
6,0,250~6,2,250
5,4,140~9,4,140
4,6,24~4,9,24
5,9,138~6,9,138
7,9,10~8,9,10
2,7,9~2,9,9
5,4,335~5,4,335
1,4,236~4,4,236
2,3,96~2,6,96
0,0,233~0,0,236
1,6,63~3,6,63
7,2,31~9,2,31
3,5,214~3,7,214
7,5,199~9,5,199
1,3,122~3,3,122
7,2,14~9,2,14
1,3,136~3,3,136
0,0,337~0,0,339
1,7,22~1,9,22
4,4,38~4,7,38
4,5,144~5,5,144
1,9,257~1,9,259
2,7,180~4,7,180
2,6,57~2,7,57
9,5,167~9,7,167
3,6,170~5,6,170
4,4,99~5,4,99
6,0,53~6,0,56
2,9,127~5,9,127
1,4,250~1,5,250
5,7,309~6,7,309
1,9,160~3,9,160
6,1,5~6,1,7
1,7,132~2,7,132
6,7,168~6,9,168
5,0,229~5,0,229
4,0,114~6,0,114
2,2,265~6,2,265
7,0,293~7,3,293
1,7,225~2,7,225
2,5,218~4,5,218
2,4,250~4,4,250
7,0,211~7,2,211
3,0,112~3,2,112
7,4,331~7,4,333
9,8,2~9,9,2
8,0,129~8,2,129
9,1,191~9,4,191
1,7,73~1,9,73
1,8,45~5,8,45
0,4,204~0,6,204
7,5,106~7,7,106
9,4,327~9,8,327
9,5,212~9,7,212
1,6,64~1,9,64
3,4,96~3,4,98
1,2,243~1,3,243
5,2,19~8,2,19
8,6,298~8,9,298
6,5,168~7,5,168
4,1,331~6,1,331
1,3,112~1,3,114
8,3,281~8,6,281
0,4,197~1,4,197
0,5,343~2,5,343
0,6,183~2,6,183
8,0,166~8,2,166
2,2,193~2,4,193
8,4,175~8,6,175
1,2,211~1,4,211
6,2,198~6,4,198
2,4,99~2,4,101
0,5,104~2,5,104
7,2,174~8,2,174
3,6,94~3,7,94
6,6,130~6,7,130
7,3,209~9,3,209
6,5,164~6,7,164
4,7,329~4,7,332
3,2,339~3,4,339
5,2,216~5,4,216
5,8,190~5,8,192
6,2,289~9,2,289
9,2,293~9,2,293
7,4,155~7,5,155
6,2,188~6,4,188
0,8,160~2,8,160
2,5,52~2,6,52
0,5,56~0,5,56
6,0,50~6,1,50
4,0,44~4,3,44
4,4,180~4,6,180
5,9,172~7,9,172
5,6,70~7,6,70
7,7,120~7,8,120
7,5,192~9,5,192
8,2,51~9,2,51
3,9,237~3,9,237
2,2,87~4,2,87
1,0,327~1,2,327
6,6,9~6,8,9
7,5,54~7,8,54
0,6,332~0,6,335
1,6,22~1,6,25
5,2,74~5,5,74
2,5,131~5,5,131
7,4,326~7,4,327
7,8,250~8,8,250
7,2,262~8,2,262
2,1,298~3,1,298
4,1,215~4,3,215
1,7,135~3,7,135
5,5,80~8,5,80
0,4,30~1,4,30
5,0,317~7,0,317
6,9,286~6,9,287
7,5,75~7,8,75
3,2,152~3,4,152
1,5,72~4,5,72
4,1,52~4,3,52
3,5,232~3,7,232
4,7,301~6,7,301
2,6,355~2,8,355
4,5,349~4,7,349
2,5,240~4,5,240
4,7,46~6,7,46
1,0,337~3,0,337
1,8,5~1,9,5
7,8,205~7,8,207
2,4,249~2,6,249
0,1,104~0,4,104
2,8,236~2,9,236
3,8,5~3,9,5
6,0,168~9,0,168
5,4,121~5,6,121
0,1,292~0,4,292
1,7,21~3,7,21
1,2,188~4,2,188
7,2,123~7,2,126
8,7,139~8,9,139
5,7,40~5,9,40
1,8,47~4,8,47
8,3,32~8,6,32
6,1,199~6,3,199
7,7,112~9,7,112
2,9,256~5,9,256
1,3,245~1,5,245
0,0,70~0,2,70
8,5,303~8,8,303
4,7,125~4,9,125
5,7,224~5,9,224
4,4,116~4,6,116
2,5,221~2,7,221
4,1,201~5,1,201
8,0,59~8,1,59
6,3,45~8,3,45
6,4,12~6,7,12
1,2,142~3,2,142
3,6,322~5,6,322
1,9,254~3,9,254
7,7,182~9,7,182
2,6,300~2,8,300
4,6,305~4,9,305
3,1,55~5,1,55
9,3,56~9,6,56
0,1,148~3,1,148
0,0,331~2,0,331
2,8,175~4,8,175
2,3,43~2,5,43
4,8,336~7,8,336
8,4,42~8,5,42
1,1,182~3,1,182
9,2,218~9,4,218
9,3,41~9,5,41
9,0,117~9,2,117
6,5,56~7,5,56
5,2,54~5,4,54
3,0,50~5,0,50
4,5,312~4,8,312
4,3,125~7,3,125
1,5,136~1,8,136
8,7,140~8,9,140
3,7,166~6,7,166
1,4,50~1,6,50
8,5,182~8,6,182
2,8,297~5,8,297
2,9,49~5,9,49
2,3,302~4,3,302
6,6,313~8,6,313
8,4,44~8,4,46
3,6,120~5,6,120
0,4,301~0,6,301
0,9,339~2,9,339
4,0,10~7,0,10
5,3,283~8,3,283
4,6,25~4,8,25
4,7,113~6,7,113
7,2,204~9,2,204
6,8,320~9,8,320
3,5,112~6,5,112
6,0,251~9,0,251
7,2,93~7,4,93
7,2,310~7,3,310
7,9,203~9,9,203
2,9,229~5,9,229
7,3,120~9,3,120
4,1,262~6,1,262
2,5,73~2,6,73
1,1,109~4,1,109
5,4,52~5,5,52
2,3,314~4,3,314
4,5,314~4,5,316
3,4,213~5,4,213
7,1,102~7,4,102
3,4,334~5,4,334
0,0,332~2,0,332
9,1,258~9,1,260
5,7,9~5,9,9
3,1,177~3,2,177
0,2,190~0,3,190
2,9,129~2,9,131
6,4,226~6,6,226
9,8,207~9,9,207
1,5,21~2,5,21
6,0,35~6,2,35
2,1,230~2,3,230
0,2,107~0,2,110
2,3,54~2,6,54
5,9,3~7,9,3
2,6,211~4,6,211
0,5,15~0,7,15
6,3,207~6,5,207
8,3,319~8,6,319
6,0,297~6,0,297
4,6,278~6,6,278
4,4,6~4,7,6
3,7,312~3,8,312
5,6,149~5,7,149
4,0,340~4,2,340
6,9,116~9,9,116
7,4,186~7,6,186
7,0,214~7,2,214
5,6,292~5,7,292
3,8,122~5,8,122
7,4,328~7,8,328
2,6,50~4,6,50
1,5,66~5,5,66
8,3,284~8,3,286
7,5,49~7,8,49
4,3,325~4,3,328
6,1,85~6,1,87
6,0,294~7,0,294
4,1,203~4,4,203
6,0,170~6,2,170
0,0,278~4,0,278
1,3,271~3,3,271
9,4,166~9,6,166
4,6,190~4,8,190
3,5,249~3,6,249
1,6,153~4,6,153
6,3,321~9,3,321
7,5,21~9,5,21
4,7,218~6,7,218
0,5,220~2,5,220
3,5,251~3,6,251
0,2,100~0,5,100
3,8,252~3,9,252
6,3,305~6,6,305
1,8,85~3,8,85
4,3,71~7,3,71
4,4,24~6,4,24
2,5,329~2,7,329
3,2,91~3,4,91
6,6,179~9,6,179
1,2,6~1,2,7
7,2,88~7,5,88
6,9,291~8,9,291
5,6,3~5,7,3
8,1,254~9,1,254
5,4,39~8,4,39
0,4,152~0,4,152
9,4,81~9,8,81
7,3,197~7,5,197
7,2,294~7,2,296
1,8,82~2,8,82
3,5,175~3,7,175
2,2,180~5,2,180
8,6,178~8,9,178
6,1,304~7,1,304
1,7,169~3,7,169
7,0,333~8,0,333
5,7,221~5,9,221
4,2,329~4,3,329
3,7,249~5,7,249
9,4,124~9,5,124
7,0,26~8,0,26
4,8,186~6,8,186
6,7,47~7,7,47
6,9,173~9,9,173
5,1,302~7,1,302
3,3,331~6,3,331
2,7,265~2,9,265
7,0,103~7,2,103
9,5,29~9,5,32
3,7,190~3,9,190
6,2,270~8,2,270
8,2,185~8,5,185
7,2,28~7,4,28
4,4,206~6,4,206
1,8,7~1,8,7
4,5,75~4,6,75
2,6,5~5,6,5
6,0,82~6,1,82
7,2,200~7,4,200
5,1,8~9,1,8
8,6,300~8,7,300
7,2,319~9,2,319
6,6,100~6,9,100
3,6,291~3,9,291
7,2,164~8,2,164
8,7,17~8,7,17
3,0,2~3,0,4
5,8,195~6,8,195
6,8,53~6,8,55
5,3,162~5,6,162
8,5,85~8,8,85
6,7,239~9,7,239
1,5,341~4,5,341
6,2,272~6,5,272
5,2,117~6,2,117
4,2,57~5,2,57
8,4,164~9,4,164
7,1,117~7,3,117
3,3,105~5,3,105
6,5,13~6,5,15
3,3,206~6,3,206
4,7,286~7,7,286
2,7,223~2,8,223
2,2,23~4,2,23
1,1,51~1,1,53
1,6,321~1,9,321
3,5,243~3,8,243
5,0,77~7,0,77
7,8,28~7,8,30
4,6,14~4,7,14
5,6,198~6,6,198
6,0,38~9,0,38
3,8,301~5,8,301
5,4,116~7,4,116
0,5,148~0,7,148
5,5,307~5,8,307
2,3,133~2,5,133
3,1,343~3,4,343
6,2,114~6,5,114
5,7,357~6,7,357
1,1,130~4,1,130
9,1,176~9,3,176
3,4,345~3,7,345
3,5,78~5,5,78
1,8,46~4,8,46
3,2,115~3,5,115
6,5,281~6,8,281
2,1,200~2,3,200
7,7,254~9,7,254
8,7,77~9,7,77
1,7,109~1,8,109
1,2,87~1,2,89
6,2,127~9,2,127
0,1,343~0,4,343
0,8,4~0,8,8
6,7,133~8,7,133
5,4,157~7,4,157
2,3,195~2,3,198
2,7,317~4,7,317
0,2,311~3,2,311
2,6,334~2,6,336
0,1,68~2,1,68
7,9,16~9,9,16
1,1,334~4,1,334
1,3,169~3,3,169
5,6,97~7,6,97
8,0,215~8,2,215
2,3,73~2,4,73
2,7,130~5,7,130
5,0,197~6,0,197
6,6,153~9,6,153
0,2,298~0,4,298
4,4,323~5,4,323
0,3,138~0,5,138
7,3,15~7,3,16
3,7,328~3,7,330
4,0,310~5,0,310
7,3,25~7,5,25
8,2,238~9,2,238
7,5,282~7,7,282
5,7,95~5,9,95
4,3,191~4,4,191
0,0,177~2,0,177
0,1,90~2,1,90
2,6,22~2,9,22
0,0,237~0,2,237
2,1,227~5,1,227
6,2,308~6,2,310
2,3,100~4,3,100
2,2,308~5,2,308
6,0,73~6,3,73
4,0,19~7,0,19
4,4,205~5,4,205
0,8,257~0,8,260
3,8,304~6,8,304
1,5,19~1,7,19
5,6,282~6,6,282
3,3,58~3,3,60
9,9,245~9,9,248
5,6,210~7,6,210
1,9,336~3,9,336
9,6,53~9,8,53
3,4,128~3,6,128
4,9,199~7,9,199""".split('\n')